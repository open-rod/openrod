package io.openrod.openrod.query;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface QueryService {

    List<QueryRequestDTO> getRecentQueries(final int numberOfQueries);

    List<QueryRequestDTO> getRecentQueriesForApp(final int numberOfQueries, final UUID appId);

    List<QueryRequestDTO> getRecentQueriesForMemory(final int numberOfQueries, final UUID memoryId);

    Page<QueryRequestDTO> searchQueries(
        final QuerySearchCriteria searchCriteria
    );
}
