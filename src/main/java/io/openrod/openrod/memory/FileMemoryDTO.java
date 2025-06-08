package io.openrod.openrod.memory;

import io.openrod.openrod.file.FileDTO;

public class FileMemoryDTO extends MemoryDTO {

    private FileDTO file;

    @Override
    public MemoryType getType() {
        return MemoryType.FILE;
    }

    public void setFile(final FileDTO file) {
        this.file = file;
    }

    public FileDTO getFile() {
        return this.file;
    }

}
