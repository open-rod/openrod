package io.openrod.openrod.category.events;

import java.util.UUID;

public class CategoryDeletedEvent {

    private final UUID id;

    public CategoryDeletedEvent(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
