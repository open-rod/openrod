package io.openrod.openrod.file.impl;

import io.openrod.openrod.file.FileDTO;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public FileDTO toDTO(final File entity) {
        var dto = new FileDTO();

        dto.setId(entity.getId());

        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setMimeType(entity.getMimeType());

        dto.setSize(entity.getSize());
        dto.setExtension(entity.getExtension());

        return dto;
    }

    public File toEntity(final FileDTO dto) {
        var entity = new File();

        entity.setId(dto.getId());

        entity.setName(dto.getName());
        entity.setPath(dto.getPath());
        entity.setMimeType(dto.getMimeType());

        entity.setSize(dto.getSize());
        entity.setExtension(dto.getExtension());

        return entity;
    }

}
