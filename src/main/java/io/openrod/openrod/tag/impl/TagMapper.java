package io.openrod.openrod.tag.impl;

import io.openrod.openrod.tag.TagDTO;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagDTO toDTO(final Tag entity) {
        var dto = new TagDTO();

        dto.setId(entity.getId());

        dto.setName(entity.getName());

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        return dto;
    }

    public Tag toEntity(final TagDTO dto) {
        var entity = new Tag();

        entity.setId(dto.getId());

        entity.setName(dto.getName());

        return entity;
    }

    public void mapEntity(final TagDTO dto, final Tag entity) {
        entity.setName(dto.getName());
    }
}
