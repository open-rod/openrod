package io.openrod.openrod.memory.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.openrod.openrod.file.impl.File;
import io.openrod.openrod.memory.MemoryType;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("FILE")
@JsonTypeName("file")
public class FileMemory extends Memory {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;

    @Override
    public MemoryType getMemoryType() {
        return MemoryType.FILE;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }
}
