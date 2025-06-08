package io.openrod.openrod.memory.events;

import io.openrod.openrod.memory.impl.Memory;

public class MemoryCreatedEvent {

    private final Memory memory;

    public MemoryCreatedEvent(
        final Memory memory
    ) {
        this.memory = memory;
    }

    public Memory getMemory() {
        return this.memory;
    }
}
