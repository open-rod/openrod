package io.openrod.openrod.query.impl;

import io.openrod.openrod.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "query_responses")
public class QueryResponse extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_id", referencedColumnName = "id", nullable = false)
    private QueryRequest request;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "memory_id")
    private UUID memoryId;

    @Column()
    private Double score;

    public void setRequest(final QueryRequest request) {
        this.request = request;
    }

    public QueryRequest getRequest() {
        return this.request;
    }

    public void setDocumentId(final String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public void setMemoryId(final UUID memoryId) {
        this.memoryId = memoryId;
    }

    public UUID getMemoryId() {
        return this.memoryId;
    }

    public void setScore(final Double score) {
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

}
