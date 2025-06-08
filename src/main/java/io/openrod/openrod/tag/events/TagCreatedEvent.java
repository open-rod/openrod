package io.openrod.openrod.tag.events;

import io.openrod.openrod.tag.TagDTO;

public class TagCreatedEvent {

    private final TagDTO tag;

    public TagCreatedEvent(final TagDTO tag) {
        this.tag = tag;
    }

    public TagDTO getTag() {
        return this.tag;
    }
}
