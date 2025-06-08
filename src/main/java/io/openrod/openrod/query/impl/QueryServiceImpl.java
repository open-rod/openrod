package io.openrod.openrod.query.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import io.openrod.openrod.ai.events.AiRequestEvent;
import io.openrod.openrod.app.AppService;
import io.openrod.openrod.memory.impl.QMemory;
import io.openrod.openrod.query.QueryRequestDTO;
import io.openrod.openrod.query.QuerySearchCriteria;
import io.openrod.openrod.query.QueryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class QueryServiceImpl implements QueryService {

    private final QueryRequestRepository queryRequestRepository;
    private final QueryRequestMapper queryRequestMapper;

    private final AppService appService;

    @Autowired
    public QueryServiceImpl(
        final QueryRequestRepository queryRequestRepository,
        final QueryRequestMapper queryRequestMapper,
        final AppService appService
    ) {
        this.queryRequestRepository = queryRequestRepository;
        this.queryRequestMapper = queryRequestMapper;

        this.appService = appService;
    }
    
    @Override
    public List<QueryRequestDTO> getRecentQueries(int numberOfQueries) {
        return this.queryRequestRepository.findAll()
            .stream()
            .map((entity) -> {
                var dto = this.queryRequestMapper.toDTO(entity);
                dto.setApp(this.appService.getApp(entity.getAppId()));

                return dto;
            })
            .toList();
    }

    @Override
    public List<QueryRequestDTO> getRecentQueriesForApp(int numberOfQueries, UUID appId) {
        return this.queryRequestRepository.findAllByAppId(appId)
            .stream()
            .map((entity) -> {
                var dto = this.queryRequestMapper.toDTO(entity);
                dto.setApp(this.appService.getApp(entity.getAppId()));

                return dto;
            })
            .toList();
    }

    @Override
    public List<QueryRequestDTO> getRecentQueriesForMemory(int numberOfQueries, UUID memoryId) {
        BooleanExpression predicate = QQueryRequest.queryRequest.isNotNull()
                .and(QQueryRequest.queryRequest.responses.any().memoryId.eq(memoryId));

        var page = PageRequest.of(0, numberOfQueries);

        return this.queryRequestRepository.findAll(predicate, page)
            .getContent()
            .stream()
            .map(entity -> {
                var dto = this.queryRequestMapper.toDTO(entity);
                dto.setApp(this.appService.getApp(entity.getAppId()));

                return dto;
            }).toList();
    }

    @EventListener(AiRequestEvent.class)
    @Async
    public void onDocumentAccessedEvent(final AiRequestEvent event) {
        var entity = new QueryRequest();

        entity.setAppId(event.getApp().getId());
        entity.setQuery(event.getQuery());

        event.getResponses()
            .forEach(r -> {
                var response = new QueryResponse();
                response.setDocumentId(r.getDocumentId());
                response.setMemoryId(r.getMemoryId());
                response.setScore(r.getScore());
                entity.addResponse(response);
            });

        this.queryRequestRepository.save(entity);
    }

    @Override
    public Page<QueryRequestDTO> searchQueries(
        final QuerySearchCriteria searchCriteria
    ) {
        BooleanExpression predicate = QQueryRequest.queryRequest.isNotNull();

        if (Objects.nonNull(searchCriteria.getAppId())) {
            predicate = predicate.and(QQueryRequest.queryRequest.appId.eq(searchCriteria.getAppId()));
        }

        if (Objects.nonNull(searchCriteria.getMemoryId())) {
            predicate = predicate.and(QQueryRequest.queryRequest.responses.any().memoryId.eq(searchCriteria.getMemoryId()));
        }

        if (Objects.nonNull(searchCriteria.getQuery())) {
            predicate = predicate.and(QQueryRequest.queryRequest.query.containsIgnoreCase(searchCriteria.getQuery()));
        }

        var page = PageRequest.of(0, 20);

        return this.queryRequestRepository.findAll(predicate, page)
            .map(entity -> {
                var dto = this.queryRequestMapper.toDTO(entity);
                dto.setApp(this.appService.getApp(entity.getAppId()));

                if (Objects.nonNull(searchCriteria.getMemoryId())) {
                    dto.setResponses(
                        dto.getResponses().stream().filter((r) -> r.getMemory().getId().equals(searchCriteria.getMemoryId())).toList()
                    );
                }
                return dto;
            });
    }
}
