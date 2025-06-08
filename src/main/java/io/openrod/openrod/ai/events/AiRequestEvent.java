package io.openrod.openrod.ai.events;

import io.openrod.openrod.app.AppDTO;

import java.util.List;
import java.util.UUID;

public class AiRequestEvent {

    private final AppDTO app;
    private final String query;
    private final List<AiResponse> responses;

    public AiRequestEvent(
        final AppDTO app,
        final String query,
        final List<AiResponse> responses
    ) {
        this.app = app;
        this.query = query;
        this.responses = responses;
    }

    public AppDTO getApp() {
        return this.app;
    }

    public String getQuery() {
        return this.query;
    }

    public List<AiResponse> getResponses() {
        return this.responses;
    }

    public static class AiResponse {

        private final UUID memoryId;
        private final String documentId;
        private final Double score;

        public AiResponse(
            final UUID memoryId,
            final String documentId,
            final Double score
        ) {
            this.memoryId = memoryId;
            this.documentId = documentId;
            this.score = score;
        }

        public UUID getMemoryId() {
            return this.memoryId;
        }

        public String getDocumentId() {
            return this.documentId;
        }

        public Double getScore() {
            return this.score;
        }
    }
}
