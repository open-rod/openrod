package io.openrod.openrod.app.impl;

import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.app.AppService;
import io.openrod.openrod.app.AppStatus;
import io.openrod.openrod.app.events.AppCreatedEvent;
import io.openrod.openrod.app.events.AppDeletedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
class AppServiceImpl implements AppService {

    private final AppRepository appRepository;
    private final AppMapper appMapper;

    private final ApplicationEventPublisher publisher;

    public AppServiceImpl(
        final AppRepository appRepository,
        final AppMapper appMapper,
        final ApplicationEventPublisher publisher
    ) {
        this.appRepository = appRepository;
        this.appMapper = appMapper;

        this.publisher = publisher;
    }

    @Override
    public List<AppDTO> getApps() {
        return this.appRepository.findAll()
            .stream()
            .map(this.appMapper::toDTO)
            .toList();
    }

    @Override
    public AppDTO getApp(UUID id) {
        return this.appRepository.findById(id)
            .map(this.appMapper::toDTO)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "App not found"));
    }

    @Override
    public AppDTO getAppByApiKey(final String apiKey) {
        return this.appRepository.findOneByApiKey(apiKey)
            .map(this.appMapper::toDTO)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "App not found"));
    }

    @Override
    public AppDTO createApp(AppDTO app) {
        app.setStatus(AppStatus.ACTIVE);
        app.setLastActive(LocalDateTime.now());
        app.setApiKey(UUID.randomUUID().toString());

        var createdEntity = appRepository.save(
            this.appMapper.toEntity(app)
        );

        this.publisher.publishEvent(new AppCreatedEvent(createdEntity));

        return this.appMapper.toDTO(createdEntity);
    }

    @Override
    public AppDTO updateApp(AppDTO app) {
        var entity = this.appRepository.findById(app.getId())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "App not found"));

        this.appMapper.mapEntity(app, entity);

        return this.appMapper.toDTO(
            appRepository.save(
                entity
            )
        );
    }

    @Override
    public void deleteApp(final UUID id) {
        var entity = this.appRepository.findById(id)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "App not found"));

        this.appRepository.softDeleteById(id);
        this.publisher.publishEvent(new AppDeletedEvent(entity));
    }

    @Override
    public List<AppDTO> getAppsByCategoryId(
        final UUID categoryId
    ) {
        return this.appRepository.findByCategoryId(categoryId)
            .stream()
            .map(this.appMapper::toDTO)
            .toList();
    }

    @Override
    public List<AppDTO> getAppsByTagId(
        final UUID tagId
    ) {
        return this.appRepository.findByTagId(tagId)
            .stream()
            .map(this.appMapper::toDTO)
            .toList();
    }
}
