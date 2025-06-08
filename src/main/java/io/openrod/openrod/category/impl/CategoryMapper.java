package io.openrod.openrod.category.impl;

import io.openrod.openrod.category.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(final Category entity) {
        var dto = new CategoryDTO();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        return dto;
    }

    public Category toEntity(final CategoryDTO dto) {
        var entity = new Category();

        entity.setId(dto.getId());

        entity.setName(dto.getName());

        return entity;
    }

    public void mapEntity(final CategoryDTO dto, final Category entity) {
        entity.setName(dto.getName());
    }
}
