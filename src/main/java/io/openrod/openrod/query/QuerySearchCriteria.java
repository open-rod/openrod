package io.openrod.openrod.query;

import java.util.UUID;

public class QuerySearchCriteria {

    private UUID memoryId;

    private UUID appId;

    private String query;

    public void setMemoryId(final UUID memoryId) {
        this.memoryId = memoryId;
    }

    public UUID getMemoryId() {
        return this.memoryId;
    }

    public void setAppId(final UUID appId) {
        this.appId = appId;
    }

    public UUID getAppId() {
        return this.appId;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
