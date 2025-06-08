package io.openrod.openrod.tag.events;

import java.util.UUID;

public class TagDeletedEvent {

    private UUID id;

    public TagDeletedEvent(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
