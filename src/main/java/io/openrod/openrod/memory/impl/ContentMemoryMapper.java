package io.openrod.openrod.memory.impl;

import io.openrod.openrod.memory.ContentMemoryDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ContentMemoryMapper {

    public ContentMemory toEntity(final ContentMemoryDTO dto) {
        var entity = new ContentMemory();

        entity.setId(dto.getId());

        entity.setStatus(dto.getStatus());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setContent(dto.getContent());

        entity.setAddedBy(dto.getAddedBy());
        entity.setAddedThrough(dto.getAddedThrough());
        entity.setAddedThroughId(dto.getAddedThroughId());

        return entity;
    }

    public ContentMemoryDTO toDTO(final ContentMemory entity) {
        var dto = new ContentMemoryDTO();

        dto.setId(entity.getId());

        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        dto.setContent(entity.getContent());

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        return dto;
    }

    public void mapEntity(final ContentMemoryDTO dto, final ContentMemory entity) {
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
    }
}
