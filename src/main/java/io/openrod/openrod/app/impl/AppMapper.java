package io.openrod.openrod.app.impl;

import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.category.impl.CategoryMapper;
import io.openrod.openrod.tag.impl.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AppMapper {

    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;

    @Autowired
    public AppMapper(
        final CategoryMapper categoryMapper,
        final TagMapper tagMapper
    ) {
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
    }

    public App toEntity(final AppDTO dto) {
        var entity = new App();

        entity.setId(dto.getId());

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setLastActive(dto.getLastActive());

        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());

        entity.setApiKey(dto.getApiKey());

        if (Objects.nonNull(dto.getCategories())) {
            entity.setCategories(dto.getCategories().stream()
                .map(this.categoryMapper::toEntity).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(dto.getTags())) {
            entity.setTags(dto.getTags().stream()
                .map(this.tagMapper::toEntity).collect(Collectors.toSet()));
        }

        return entity;
    }

    public AppDTO toDTO(final App entity) {
        var dto = new AppDTO();

        dto.setId(entity.getId());

        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        dto.setLastActive(entity.getLastActive());

        dto.setStatus(entity.getStatus());
        dto.setType(entity.getType());

        dto.setApiKey(entity.getApiKey());

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        if (Objects.nonNull(entity.getCategories())) {
            dto.setCategories(entity.getCategories().stream().map(this.categoryMapper::toDTO).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(entity.getTags())) {
            dto.setTags(entity.getTags().stream().map(this.tagMapper::toDTO).collect(Collectors.toSet()));
        }

        return dto;
    }

    public void mapEntity(final AppDTO dto, final App entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setStatus(dto.getStatus());

        entity.setLastActive(dto.getLastActive());
        entity.setType(dto.getType());

        if (Objects.nonNull(dto.getCategories())) {
            entity.setCategories(dto.getCategories().stream()
                .map(this.categoryMapper::toEntity).collect(Collectors.toSet()));
        }

        if (Objects.nonNull(dto.getTags())) {
            entity.setTags(dto.getTags().stream()
                .map(this.tagMapper::toEntity).collect(Collectors.toSet()));
        }
    }
}
