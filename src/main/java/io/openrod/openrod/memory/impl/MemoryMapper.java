package io.openrod.openrod.memory.impl;

import io.openrod.openrod.category.impl.CategoryMapper;
import io.openrod.openrod.memory.FileMemoryDTO;
import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.memory.MemoryType;
import io.openrod.openrod.memory.ContentMemoryDTO;
import io.openrod.openrod.tag.impl.TagMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MemoryMapper {

    private final FileMemoryMapper fileMemoryMapper;
    private final ContentMemoryMapper contentMemoryMapper;

    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    public MemoryMapper(
        final FileMemoryMapper fileMemoryMapper,
        final ContentMemoryMapper contentMemoryMapper,
        final CategoryMapper categoryMapper,
        final TagMapper tagMapper
    ) {
        this.fileMemoryMapper = fileMemoryMapper;
        this.contentMemoryMapper = contentMemoryMapper;

        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
    }

    public MemoryDTO toDTO(final Memory entity) {
        var dto = entity.getMemoryType().equals(MemoryType.CONTENT)
            ? this.contentMemoryMapper.toDTO((ContentMemory) entity)
            : this.fileMemoryMapper.toDTO((FileMemory) entity);

        if (Objects.nonNull(entity.getCategory())) {
            dto.setCategory(this.categoryMapper.toDTO(entity.getCategory()));
        }

        dto.setAddedBy(entity.getAddedBy());
        dto.setAddedThrough(entity.getAddedThrough());
        dto.setAddedThroughId(entity.getAddedThroughId());

        if (Objects.nonNull(entity.getTags())) {
            dto.setTags(
                entity.getTags().stream().map(this.tagMapper::toDTO).toList()
            );
        }

        return dto;
    }

    public Memory toEntity(final MemoryDTO dto) {
        var entity = dto.getType().equals(MemoryType.CONTENT)
            ? this.contentMemoryMapper.toEntity((ContentMemoryDTO) dto)
            : this.fileMemoryMapper.toEntity((FileMemoryDTO) dto);

        if (Objects.nonNull(dto.getTags())) {
            entity.setTags(dto.getTags().stream().map(this.tagMapper::toEntity).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(dto.getCategory())) {
            entity.setCategory(this.categoryMapper.toEntity(dto.getCategory()));
        }

        return entity;
    }

    public void mapEntity(final MemoryDTO dto, final Memory entity) {
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());

        if (Objects.nonNull(dto.getTags())) {
            entity.setTags(dto.getTags().stream().map(this.tagMapper::toEntity).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(dto.getCategory())) {
            entity.setCategory(this.categoryMapper.toEntity(dto.getCategory()));
        }
    }

}