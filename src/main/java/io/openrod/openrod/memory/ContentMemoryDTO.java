package io.openrod.openrod.memory;

public class ContentMemoryDTO extends MemoryDTO {

    private String content;

    public void setContent(final String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public MemoryType getType() {
        return MemoryType.CONTENT;
    }
}
