package io.openrod.openrod.memory.events;

import io.openrod.openrod.memory.MemoryDTO;

public class MemoryDeletedEvent {

    private final MemoryDTO memory;

    public MemoryDeletedEvent(final MemoryDTO memory) {
        this.memory = memory;
    }

    public MemoryDTO getMemory() {
        return this.memory;
    }
}
