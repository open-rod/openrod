package io.openrod.openrod.file.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import io.openrod.openrod.common.entity.BaseEntity;

@Entity
@Table(name = "files")
public class File extends BaseEntity {

    @Column()
    private String name;

    @Column()
    private String path;

    @Column()
    private String mimeType;

    @Column()
    private Long size;

    @Column()
    private String extension;

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setMimeType(final String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setSize(final Long size) {
        this.size = size;
    }

    public Long getSize() {
        return this.size;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }
}
