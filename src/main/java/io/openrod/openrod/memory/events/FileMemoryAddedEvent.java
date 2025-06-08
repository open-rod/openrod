package io.openrod.openrod.memory.events;

import io.openrod.openrod.memory.FileMemoryDTO;

public class FileMemoryAddedEvent {

    private FileMemoryDTO memory;

    public FileMemoryAddedEvent(final FileMemoryDTO memory) {
        this.memory = memory;
    }

    public FileMemoryDTO getMemory() {
        return this.memory;
    }
}
