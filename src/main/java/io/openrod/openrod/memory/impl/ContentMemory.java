package io.openrod.openrod.memory.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.openrod.openrod.memory.MemoryType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTENT")
@JsonTypeName("content")
public class ContentMemory extends Memory {

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Override
    public MemoryType getMemoryType() {
        return MemoryType.CONTENT;
    }

    public String getContent() { return this.content; }
    public void setContent(final String content) { this.content = content; }

}