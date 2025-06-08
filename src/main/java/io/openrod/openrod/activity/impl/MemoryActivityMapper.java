package io.openrod.openrod.activity.impl;

import io.openrod.openrod.activity.MemoryActivityDTO;
import io.openrod.openrod.memory.impl.MemoryMapper;
import org.springframework.stereotype.Component;

@Component
public class MemoryActivityMapper {

    private final MemoryMapper memoryMapper;

    public MemoryActivityMapper(
        final MemoryMapper memoryMapper
    ) {
        this.memoryMapper = memoryMapper;
    }

    public MemoryActivityDTO toDTO(final MemoryActivity entity) {
        var dto = new MemoryActivityDTO();

        dto.setId(entity.getId());
        dto.setMemory(
            this.memoryMapper.toDTO(entity.getMemory())
        );
        dto.setAction(entity.getAction());
        dto.setSeverity(entity.getSeverity());

        dto.setLastModified(entity.getLastModified());
        dto.setCreated(entity.getCreated());

        return dto;
    }
}
