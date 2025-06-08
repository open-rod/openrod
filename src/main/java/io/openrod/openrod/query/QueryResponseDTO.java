package io.openrod.openrod.query;

import io.openrod.openrod.common.dto.BaseDTO;
import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.query.impl.QueryRequest;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

public class QueryResponseDTO extends BaseDTO {

    private String documentId;
    private MemoryDTO memory;
    private Double score;

    public void setDocumentId(final String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setMemory(final MemoryDTO memory) {
        this.memory = memory;
    }

    public MemoryDTO getMemory() {
        return this.memory;
    }

    public void setScore(final Double score) {
        this.score = score;
    }

    public Double getScore() {
        return this.score;
    }
}
