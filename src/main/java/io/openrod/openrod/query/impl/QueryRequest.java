package io.openrod.openrod.query.impl;

import io.openrod.openrod.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity()
@Table(name = "query_requests")
public class QueryRequest extends BaseEntity {

    @Column()
    private String query;

    @Column(name = "app_id")
    private UUID appId;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QueryResponse> responses;

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setAppId(final UUID appId) {
        this.appId = appId;
    }

    public UUID getAppId() {
        return this.appId;
    }

    public void setResponses(final Set<QueryResponse> responses) {
        this.responses = responses;
    }

    public void addResponse(final QueryResponse response) {
        if (Objects.isNull(this.responses)) {
            this.responses = new HashSet<>();
        }

        response.setRequest(this);
        this.responses.add(response);
    }

    public Set<QueryResponse> getResponses() {
        return this.responses;
    }
}
