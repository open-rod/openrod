package io.openrod.openrod.memory.events;

import io.openrod.openrod.memory.ContentMemoryDTO;

public class ContentMemoryAddedEvent {

    private final ContentMemoryDTO memory;

    public ContentMemoryAddedEvent(
        final ContentMemoryDTO memory
    ) {
        this.memory = memory;
    }

    public ContentMemoryDTO getMemory() {
        return this.memory;
    }
}
