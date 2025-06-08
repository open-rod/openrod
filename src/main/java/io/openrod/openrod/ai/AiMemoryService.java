package io.openrod.openrod.ai;

import io.openrod.openrod.ai.documentloaders.DocumentLoaderProvider;
import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.common.dto.BaseDTO;
import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.memory.MemoryService;
import io.openrod.openrod.memory.MemoryStatus;
import io.openrod.openrod.memory.events.ContentMemoryAddedEvent;
import io.openrod.openrod.memory.events.FileMemoryAddedEvent;
import io.openrod.openrod.memory.events.MemoryDeletedEvent;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;

@Service
public class AiMemoryService {

    private final RodVectorStore vectorStore;
    private final MemoryService memoryService;
    private final DocumentLoaderProvider documentLoaderProvider;

    @Autowired
    public AiMemoryService(
        final RodVectorStore vectorStore,
        final MemoryService memoryService,
        final DocumentLoaderProvider documentLoaderProvider
    ) {
        this.vectorStore = vectorStore;
        this.memoryService = memoryService;

        this.documentLoaderProvider = documentLoaderProvider;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onFileMemoryAddedEvent(final FileMemoryAddedEvent event) {
        final var memory = event.getMemory();

        final var documentLoader = this.documentLoaderProvider.getDocumentLoader(memory);

        memory.setStatus(MemoryStatus.PROCESSING);
        this.memoryService.updateMemory(memory);

        final var documents = documentLoader.loadDocuments(memory)
                .stream().map((document) -> {
                    var metadata = getMetadata(memory);
                    metadata.putAll(
                            document.getMetadata()
                    );

                    return new Document(
                        document.getText(),
                        metadata
                    );
                }).toList();

        for (int i = 0; i < documents.size(); i++) {
            documents.get(i).getMetadata().put("order", i);
        }

        this.vectorStore.add(documents);

        memory.setStatus(MemoryStatus.ACTIVE);
        this.memoryService.updateMemory(memory);
    }

    @Async
    public void onMemoryDeletedEvent(final MemoryDeletedEvent event) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.eq("memory_id", event.getMemory().getId().toString()).build();

        this.vectorStore.delete(expression);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onContentMemoryAddedEvent(final ContentMemoryAddedEvent event) {
        final var memory = event.getMemory();

        memory.setStatus(MemoryStatus.PROCESSING);
        this.memoryService.updateMemory(memory);

        var document = new Document(
            memory.getContent(),
            getMetadata(memory)
        );

        document.getMetadata().put("order", 0);

        this.vectorStore.add(List.of(document));

        memory.setStatus(MemoryStatus.ACTIVE);

        this.memoryService.updateMemory(memory);
    }

    private Map<String, Object> getMetadata(final MemoryDTO memory) {
        var metadata = new HashMap<String, Object>();

        metadata.put("memory_id", memory.getId().toString());

        if (Objects.nonNull(memory.getTags())) {
            metadata.put("tags", memory.getTags().stream().map(BaseDTO::getId).map(UUID::toString).toList());
        }

        if (Objects.nonNull(memory.getCategory())) {
            metadata.put("category_id", memory.getCategory().getId().toString());
        }

        return metadata;
    }

    public UUID getMostRelevantCategory(final AppDTO app, final MemoryDTO memory) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.and(
            b.eq("type", "category"),
            b.in("category_id", app.getCategories().stream().map(BaseDTO::getId).map(UUID::toString).toArray(String[]::new))
        ).build();

        var searchRequest = SearchRequest.builder()
            .query(memory.getDescription())
            .filterExpression(expression)
            .topK(1)
            .build();

        var documents = this.vectorStore.similaritySearch(searchRequest);

        if (documents.isEmpty()) {
            return null;
        }

        return UUID.fromString(documents.get(0).getMetadata().get("category_id").toString());
    }

    public List<UUID> getMostRelevantTags(final AppDTO app, final MemoryDTO memory, final int numberOfTags) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.and(
                b.eq("type", "tag"),
                b.in("tag_id", app.getTags().stream().map(BaseDTO::getId).map(UUID::toString).toArray(String[]::new))
        ).build();

        var searchRequest = SearchRequest.builder()
            .query(memory.getDescription())
            .filterExpression(expression)
            .topK(numberOfTags)
            .build();

        var documents = this.vectorStore.similaritySearch(searchRequest);

        if (documents.isEmpty()) {
            return null;
        }

        return documents.stream().map(d -> UUID.fromString(d.getMetadata().get("tag_id").toString())).toList();
    }

    public List<Document> getDocumentsForMemory(final UUID memoryId) {
        return this.vectorStore.getDocumentsForMemory(memoryId);
    }

}
