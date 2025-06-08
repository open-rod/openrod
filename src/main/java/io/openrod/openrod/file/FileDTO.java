package io.openrod.openrod.file;

import io.openrod.openrod.common.dto.BaseDTO;

public class FileDTO extends BaseDTO {

    private String name;
    private String path;
    private String mimeType;
    private Long size;
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
