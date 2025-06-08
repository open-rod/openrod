package io.openrod.openrod.tag.impl;

import io.openrod.openrod.app.AppService;
import io.openrod.openrod.memory.MemoryService;
import io.openrod.openrod.tag.TagDTO;
import io.openrod.openrod.tag.TagInfoDO;
import io.openrod.openrod.tag.TagService;
import io.openrod.openrod.tag.events.TagCreatedEvent;
import io.openrod.openrod.tag.events.TagDeletedEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    private final MemoryService memoryService;
    private final AppService appService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public TagServiceImpl(
        final TagRepository tagRepository,
        final TagMapper tagMapper,
        final MemoryService memoryService,
        final AppService appService,
        final ApplicationEventPublisher publisher
    ) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;

        this.memoryService = memoryService;
        this.appService = appService;

        this.publisher = publisher;
    }

    @Override
    public List<TagInfoDO> getTags() {
        return this.tagRepository.findAll()
            .stream()
            .map(this.tagMapper::toDTO)
            .map((tag) ->
                new TagInfoDO(
                    tag,
                    this.appService.getAppsByTagId(tag.getId()).size(),
                    this.memoryService.getMemoriesByTagId(tag.getId()).size()
                )
            )
            .toList();
    }

    @Override
    public TagDTO getTag(UUID id) {
        return this.tagRepository.findById(id)
            .map(this.tagMapper::toDTO)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Tag not found"));
    }

    @Override
    public TagDTO createTag(TagDTO tag) {
        var entity = this.tagMapper.toEntity(tag);

        var createdEntity = this.tagRepository.save(entity);

        var createdDto = this.tagMapper.toDTO(createdEntity);

        this.publisher.publishEvent(new TagCreatedEvent(createdDto));

        return createdDto;
    }

    @Override
    public TagDTO updateTag(TagDTO tag) {
        var entity = this.tagRepository.findById(tag.getId())
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Tag not found"));

        this.tagMapper.mapEntity(tag, entity);

        var updatedEntity = this.tagRepository.save(entity);

        return this.tagMapper.toDTO(updatedEntity);
    }

    @Override
    public void deleteTag(UUID id) {
        this.tagRepository.deleteById(id);

        this.publisher.publishEvent(new TagDeletedEvent(id));
    }
}
