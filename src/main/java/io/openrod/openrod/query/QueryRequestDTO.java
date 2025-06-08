package io.openrod.openrod.query;

import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.common.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryRequestDTO extends BaseDTO {

    private AppDTO app;

    private String query;

    private List<QueryResponseDTO> responses;

    public void setApp(final AppDTO app) {
        this.app = app;
    }

    public AppDTO getApp() {
        return this.app;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setResponses(final List<QueryResponseDTO> responses) {
        this.responses = responses;
    }

    public void addResponse(final QueryResponseDTO response) {
        if (Objects.isNull(this.responses)) {
            this.responses = new ArrayList<>();
        }

        this.responses.add(response);
    }

    public List<QueryResponseDTO> getResponses() {
        return this.responses;
    }
}
