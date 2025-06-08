package io.openrod.openrod.activity.impl;

import io.openrod.openrod.activity.ActivityDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

@Component
public class ActivityMapper {

    private final AppActivityMapper appActivityMapper;
    private final MemoryActivityMapper memoryActivityMapper;

    public ActivityMapper(
        final AppActivityMapper appActivityMapper,
        final MemoryActivityMapper memoryActivityMapper
    ) {
        this.appActivityMapper = appActivityMapper;
        this.memoryActivityMapper = memoryActivityMapper;
    }

    public ActivityDTO toDTO(final Activity activity) {
        if (activity.getType().equals(ActivityType.APP)) {
            return this.appActivityMapper.toDTO((AppActivity) activity);
        } else if (activity.getType().equals(ActivityType.MEMORY)) {
            return this.memoryActivityMapper.toDTO((MemoryActivity) activity);
        }
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Could not map activity type %s".formatted(activity.getType().toString()));
    }
}
