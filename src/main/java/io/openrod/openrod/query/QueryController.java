package io.openrod.openrod.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/queries")
public class QueryController {

    private final QueryService queryService;

    @Autowired
    public QueryController(
        final QueryService queryService
    ) {
        this.queryService = queryService;
    }

    @GetMapping("/memories/recent/{id}")
    public ResponseEntity<List<QueryRequestDTO>> getRecentQueriesForMemory(
        @PathVariable("id") final UUID memoryId
    ) {
        return ResponseEntity.ok(
            this.queryService.getRecentQueriesForMemory(5, memoryId)
        );
    }

    @GetMapping("/apps/recent/{id}")
    public ResponseEntity<List<QueryRequestDTO>> getRecentQueriesForApp(
        @PathVariable("id") final UUID appId
    ) {
        return ResponseEntity.ok(
            this.queryService.getRecentQueriesForApp(5, appId)
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<List<QueryRequestDTO>> getRecentQueries() {
        return ResponseEntity.ok(
            this.queryService.getRecentQueries(5)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<QueryRequestDTO>> searchQueries(
        @ModelAttribute final QuerySearchCriteria searchCriteria
    ) {
        return ResponseEntity.ok(
            this.queryService.searchQueries(searchCriteria)
        );
    }
}
