package io.openrod.openrod.ai.events;

import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.sse.event.SseEvent;

import java.util.List;

public class AiRequestSseEvent extends SseEvent<AiRequestSseEvent.AiRequestData> {

    private final AiRequestData data;

    public AiRequestSseEvent(final AiRequestData data) {
        this.data = data;
    }

    @Override
    public String eventName() {
        return "ai.request";
    }

    @Override
    public AiRequestData data() {
        return this.data;
    }

    public static class AiRequestData {
        private final String query;
        private final AppDTO app;
        private final List<AiRequestEvent.AiResponse> responses;

        public AiRequestData(final String query, final AppDTO app, final List<AiRequestEvent.AiResponse> responses) {
            this.query = query;
            this.app = app;
            this.responses = responses;
        }

        public String getQuery() {
            return this.query;
        }

        public AppDTO getApp() {
            return this.app;
        }

        public List<AiRequestEvent.AiResponse> getResponses() {
            return this.responses;
        }
    }
}
