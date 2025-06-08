package io.openrod.openrod.ai;

import io.openrod.openrod.category.events.CategoryCreatedEvent;
import io.openrod.openrod.category.events.CategoryDeletedEvent;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;
import java.util.List;

@Service
public class AiCategoryService {

    private final RodVectorStore vectorStore;

    @Autowired
    public AiCategoryService(
        final RodVectorStore vectorStore
    ) {
        this.vectorStore = vectorStore;
    }

    @TransactionalEventListener
    @Async
    public void onCategoryCreated(final CategoryCreatedEvent event) {
        var metadata = new HashMap<String, Object>();
        metadata.put("type", "category");
        metadata.put("category_id", event.getCategory().getId().toString());

        var document = new Document(event.getCategory().getName(), metadata);

        this.vectorStore.add(List.of(document));
    }

    @TransactionalEventListener
    @Async
    public void onCategoryDeleted(final CategoryDeletedEvent event) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression expression = b.and(
            b.eq("category_id", event.getId().toString()),
            b.eq("type", "category")
        ).build();

        this.vectorStore.delete(expression);
    }
}
