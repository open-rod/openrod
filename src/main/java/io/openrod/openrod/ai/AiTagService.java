package io.openrod.openrod.ai;

import io.openrod.openrod.category.events.CategoryCreatedEvent;
import io.openrod.openrod.category.events.CategoryDeletedEvent;
import io.openrod.openrod.tag.events.TagCreatedEvent;
import io.openrod.openrod.tag.events.TagDeletedEvent;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.List;

@Component
public class AiTagService {

    private final RodVectorStore vectorStore;

    @Autowired
    public AiTagService(
        final RodVectorStore vectorStore
    ) {
        this.vectorStore = vectorStore;
    }

    @TransactionalEventListener
    @Async
    public void onTagCreated(final TagCreatedEvent event) {
        var metadata = new HashMap<String, Object>();
        metadata.put("type", "tag");
        metadata.put("tag_id", event.getTag().getId().toString());

        var document = new Document(event.getTag().getName(), metadata);

        this.vectorStore.add(List.of(document));
    }

    @TransactionalEventListener
    @Async
    public void onTagDeleted(final TagDeletedEvent event) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.and(
                b.eq("tag_id", event.getId().toString()),
                b.eq("type", "tag")
        ).build();

        this.vectorStore.delete(expression);
    }
}

