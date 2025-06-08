package io.openrod.openrod.ai;

import io.openrod.openrod.ai.events.AiRequestEvent;
import io.openrod.openrod.ai.events.AiRequestSseEvent;
import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.common.dto.BaseDTO;
import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.memory.MemoryService;
import io.openrod.openrod.memory.MemoryStatus;
import io.openrod.openrod.tag.TagService;
import io.qdrant.client.QdrantClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RodVectorStore {

    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;
    private final MemoryService memoryService;
    private final TagService tagService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public RodVectorStore(
        final VectorStore vectorStore,
        final EmbeddingModel embeddingModel,
        final MemoryService memoryService,
        final QdrantClient qdrantClient,
        final TagService tagService,
        final ApplicationEventPublisher publisher
    ) {
        this.vectorStore = vectorStore;
        this.embeddingModel = embeddingModel;

        this.memoryService = memoryService;
        this.tagService = tagService;

        this.publisher = publisher;
    }

    public List<RodDocument> searchMemories(final String query, final AppDTO app) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        var memories = this.memoryService.getMemoriesByCategories(
            app.getCategories().stream().map(BaseDTO::getId).toList()
        ).stream().filter((memory) -> memory.getStatus().equals(MemoryStatus.ACTIVE)).toList();

        var expression = b.in("memory_id", memories.stream().map(BaseDTO::getId).map(UUID::toString).toArray(String[]::new)).build();

        var searchRequest = SearchRequest.builder()
            .query(query)
            .filterExpression(expression)
            .topK(2)
            .build();

        var documents = this.vectorStore.similaritySearch(searchRequest);

        if (Objects.isNull(documents)) {
            return Collections.emptyList();
        }

        var responses = new ArrayList<AiRequestEvent.AiResponse>();

        var aiDocuments = documents.stream()
            .map(document -> {
                final MemoryDTO memory = this.memoryService.getMemory(UUID.fromString(document.getMetadata().get("memory_id").toString()));
                responses.add(new AiRequestEvent.AiResponse(
                    memory.getId(),
                    document.getId(),
                    document.getScore()
                ));

                return new RodDocument(document.getText(), memory.getTags(), document.getScore());
            }).toList();

        this.publisher.publishEvent(
            new AiRequestEvent(
                app,
                query,
                responses
            )
        );

        this.publisher.publishEvent(
                new AiRequestSseEvent(
                    new AiRequestSseEvent.AiRequestData(
                        query,
                        app,
                        responses
                    )
                )
        );

        return aiDocuments;
    }


    public void add(List<Document> documents) {
        this.vectorStore.add(documents);
    }

    public void delete(final Filter.Expression filterExpression) {
        this.vectorStore.delete(filterExpression);
    }

    public List<Document> similaritySearch(final SearchRequest searchRequest) {
        return this.vectorStore.similaritySearch(searchRequest);
    }

    public List<Document> getDocumentsForMemory(final UUID memoryId) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        var searchRequest = SearchRequest.builder()
                .query("")
                .filterExpression(b.eq("memory_id", memoryId.toString()).build())
                .topK(1000)
                .build();

        return this.vectorStore.similaritySearch(searchRequest);
    }

}
