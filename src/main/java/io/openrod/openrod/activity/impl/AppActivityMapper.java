package io.openrod.openrod.activity.impl;

import io.openrod.openrod.activity.AppActivityDTO;
import io.openrod.openrod.app.impl.AppMapper;
import org.springframework.stereotype.Component;

@Component
public class AppActivityMapper {

    private final AppMapper appMapper;

    public AppActivityMapper(
        final AppMapper appMapper
    ) {
        this.appMapper = appMapper;
    }

    public AppActivityDTO toDTO(final AppActivity entity) {
        var dto = new AppActivityDTO();

        dto.setId(entity.getId());

        dto.setSeverity(entity.getSeverity());
        dto.setAction(entity.getAction());

        dto.setApp(
            this.appMapper.toDTO(entity.getApp())
        );

        dto.setCreated(entity.getCreated());
        dto.setLastModified(entity.getLastModified());

        return dto;
    }
}
