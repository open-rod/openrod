package io.openrod.openrod.category.events;

import io.openrod.openrod.category.CategoryDTO;

public class CategoryCreatedEvent {

    private final CategoryDTO category;

    public CategoryCreatedEvent(
        final CategoryDTO category
    ) {
        this.category = category;
    }

    public CategoryDTO getCategory() {
        return this.category;
    }
}
