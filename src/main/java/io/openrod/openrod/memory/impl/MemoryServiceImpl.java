package io.openrod.openrod.memory.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.openrod.openrod.category.impl.CategoryRepository;
import io.openrod.openrod.memory.*;
import io.openrod.openrod.memory.events.*;
import io.openrod.openrod.tag.impl.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemoryServiceImpl implements MemoryService {

    private final MemoryRepository memoryRepository;
    private final MemoryMapper memoryMapper;
    private final ApplicationEventPublisher publisher;

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    public MemoryServiceImpl(
        final MemoryRepository memoryRepository,
        final MemoryMapper memoryMapper,
        final ApplicationEventPublisher publisher,
        final CategoryRepository categoryRepository,
        final TagRepository tagRepository
    ) {
        this.memoryRepository = memoryRepository;
        this.memoryMapper = memoryMapper;

        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;

        this.publisher = publisher;
    }

    @Override
    public List<MemoryDTO> getMemories() {
        return this.memoryRepository.findAll()
            .stream()
            .map(this.memoryMapper::toDTO)
            .toList();
    }

    @Override
    public Page<MemoryDTO> searchMemories(
        final MemorySearchCriteria searchCriteria
    ) {
        BooleanExpression predicate = QMemory.memory.isNotNull()
                .and(QMemory.memory.deleted.isFalse());

        if (Objects.nonNull(searchCriteria.getName())) {
            predicate = predicate.and(QMemory.memory.title.containsIgnoreCase(searchCriteria.getName()));
        }

        if (Objects.nonNull(searchCriteria.getCategories()) && !searchCriteria.getCategories().isEmpty()) {
            predicate = predicate.and(QMemory.memory.category.id.in(searchCriteria.getCategories()));
        }

        if (Objects.nonNull(searchCriteria.getTags()) && !searchCriteria.getTags().isEmpty()) {
            predicate = predicate.and(QMemory.memory.tags.any().id.in(searchCriteria.getTags()));
        }


        final var pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "created"));

        return this.memoryRepository.findAll(predicate, pageable)
            .map(this.memoryMapper::toDTO);
    }

    @Override
    public MemoryDTO getMemory(UUID id) {
        return this.memoryRepository.findById(id)
            .map(this.memoryMapper::toDTO)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Memory not found"));
    }

    @Override
    public MemoryDTO createMemory(MemoryDTO memory) {
        var entity = this.memoryMapper.toEntity(memory);

        entity.setStatus(MemoryStatus.QUEUED);

        if (Objects.nonNull(memory.getCategory())) {
            entity.setCategory(
                this.categoryRepository.findById(
                    memory.getCategory().getId()
                ).orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Category not found"))
            );
        }

        if (Objects.nonNull(memory.getTags())) {
            entity.setTags(memory.getTags().stream().map(tag -> this.tagRepository.findById(tag.getId()).orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Tag not found"))).collect(Collectors.toSet()));
        }

        var createdEntity = this.memoryRepository.save(entity);

        this.publisher.publishEvent(new MemoryCreatedEvent(createdEntity));

        var createdDto = this.memoryMapper.toDTO(createdEntity);

        if (createdDto.getType().equals(MemoryType.FILE)) {
            this.publisher.publishEvent(
                new FileMemoryAddedEvent(
                    (FileMemoryDTO) createdDto
                )
            );
        } else if (createdDto.getType().equals(MemoryType.CONTENT)) {
            this.publisher.publishEvent(
                new ContentMemoryAddedEvent(
                    (ContentMemoryDTO) createdDto
                )
            );
        }

        return createdDto;
    }

    @Override
    public MemoryDTO updateMemory(MemoryDTO memory) {
        var entity = this.memoryRepository.findById(memory.getId())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Memory not found"));

        this.memoryMapper.mapEntity(memory, entity);

        var updatedMemory = this.memoryMapper.toDTO(
            this.memoryRepository.save(entity)
        );

        this.publisher.publishEvent(new MemoryUpdatedEvent(updatedMemory));

        return updatedMemory;
    }

    @Override
    public void deleteMemory(final UUID id) {
        var memory = this.getMemory(id);

        this.memoryRepository.softDeleteById(id);

        this.publisher.publishEvent(new MemoryDeletedEvent(memory));
    }

    @Override
    public List<MemoryDTO> getMemoriesByCategories(List<UUID> categories) {
        return this.memoryRepository.findByCategoryIdIn(categories)
            .stream()
            .map(this.memoryMapper::toDTO)
            .toList();
    }

    @Override
    public List<MemoryDTO> getMemoriesByCategoryId(
            final UUID categoryId
    ) {
        return this.memoryRepository.findByCategoryId(categoryId)
            .stream()
            .map(this.memoryMapper::toDTO)
            .toList();
    }

    @Override
    public List<MemoryDTO> getMemoriesByTagId(
        final UUID tagId
    ) {
        return this.memoryRepository.findByTagId(tagId)
            .stream()
            .map(this.memoryMapper::toDTO)
            .toList();
    }
}
