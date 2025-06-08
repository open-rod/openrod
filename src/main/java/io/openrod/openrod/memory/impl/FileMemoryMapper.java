package io.openrod.openrod.memory.impl;

import io.openrod.openrod.file.impl.FileMapper;
import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FileMemoryMapper {

    private final FileMapper fileMapper;

    public FileMemoryMapper(
        final FileMapper fileMapper
    ) {
        this.fileMapper = fileMapper;
    }

    public FileMemory toEntity(final FileMemoryDTO dto) {
        var entity = new FileMemory();

        entity.setId(dto.getId());

        entity.setStatus(dto.getStatus());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setFile(
            this.fileMapper.toEntity(dto.getFile())
        );

        entity.setAddedBy(dto.getAddedBy());
        entity.setAddedThrough(dto.getAddedThrough());
        entity.setAddedThroughId(dto.getAddedThroughId());

        return entity;
    }

    public FileMemoryDTO toDTO(final FileMemory entity) {
        var dto = new FileMemoryDTO();

        dto.setId(entity.getId());

        dto.setStatus(entity.getStatus());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());

        dto.setFile(
            this.fileMapper.toDTO(entity.getFile())
        );

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        return dto;
    }

    public void mapEntity(final FileMemoryDTO dto, final FileMemory entity) {
        entity.setTitle(dto.getTitle());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());
    }
}

