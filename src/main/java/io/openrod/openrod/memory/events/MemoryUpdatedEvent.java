package io.openrod.openrod.memory.events;

import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.sse.event.SseEvent;

public class MemoryUpdatedEvent extends SseEvent<MemoryDTO> {

    private final MemoryDTO memory;

    public MemoryUpdatedEvent(final MemoryDTO memory) {
        this.memory = memory;
    }

    @Override
    public String eventName() {
        return "memory.updated";
    }

    @Override
    public MemoryDTO data() {
        return this.memory;
    }
}
