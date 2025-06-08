package io.openrod.openrod.query.impl;

import io.openrod.openrod.memory.MemoryService;
import io.openrod.openrod.query.QueryRequestDTO;
import io.openrod.openrod.query.QueryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

@Component
public class QueryRequestMapper {

    private final MemoryService memoryService;

    @Autowired
    public QueryRequestMapper(
        final MemoryService memoryService
    ) {
        this.memoryService = memoryService;
    }

    public QueryRequestDTO toDTO(final QueryRequest entity) {
        var dto = new QueryRequestDTO();

        dto.setId(entity.getId());

        dto.setQuery(entity.getQuery());

        if (Objects.nonNull(entity.getResponses())) {
            entity.getResponses().forEach(r -> {
                var response = new QueryResponseDTO();
                response.setId(r.getId());
                response.setDocumentId(r.getDocumentId());
                response.setScore(r.getScore());
                response.setCreated(r.getCreated());
                response.setLastModified(r.getLastModified());
                response.setMemory(
                    this.memoryService.getMemory(r.getMemoryId())
                );
                dto.addResponse(response);
            });
        }

        return dto;
    }
}
