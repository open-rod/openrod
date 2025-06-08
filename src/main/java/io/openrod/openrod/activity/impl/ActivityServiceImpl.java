package io.openrod.openrod.activity.impl.;

import io.openrod.openrod.activity.*;
import io.openrod.openrod.app.events.AppCreatedEvent;
import io.openrod.openrod.app.events.AppDeletedEvent;
import io.openrod.openrod.memory.events.MemoryCreatedEvent;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(
        final ActivityRepository activityRepository,
        final ActivityMapper activityMapper
    ) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public List<ActivityDTO> getRecentActivities() {
        return this.activityRepository.findAll(
                PageRequest.of(0, 5, Sort.Direction.DESC, "created")
        )
            .getContent()
            .stream()
            .map(this.activityMapper::toDTO)
            .toList();
    }

    @Override
    public List<ActivityDTO> getRecentAppActivities() {
        return this.activityRepository.findByEntityType(
            AppActivity.class,
            PageRequest.of(0, 5, Sort.Direction.DESC, "created")
        ).stream().map(this.activityMapper::toDTO).toList();
    }

    @Override
    public List<ActivityDTO> getRecentMemoryActivities() {
        return this.activityRepository.findByEntityType(
                MemoryActivity.class,
                PageRequest.of(0, 5, Sort.Direction.DESC, "created")
        ).stream().map(this.activityMapper::toDTO).toList();
    }

    @EventListener(AppCreatedEvent.class)
    public void onAppCreated(final AppCreatedEvent event) {
        var activity = new AppActivity();

        activity.setApp(event.getApp());
        activity.setSeverity(Severity.SUCCESS);
        activity.setAction(AppActivityAction.CREATED);

        this.activityRepository.save(activity);
    }

    @EventListener(AppDeletedEvent.class)
    public void onAppDeleted(final AppDeletedEvent event) {
        var activity = new AppActivity();

        activity.setApp(event.getApp());
        activity.setSeverity(Severity.SUCCESS);
        activity.setAction(AppActivityAction.DELETED);

        this.activityRepository.save(activity);
    }

    @EventListener(MemoryCreatedEvent.class)
    public void onMemoryCrated(final MemoryCreatedEvent event) {
        var activity = new MemoryActivity();

        activity.setMemory(event.getMemory());
        activity.setSeverity(Severity.SUCCESS);
        activity.setAction(MemoryActivityAction.CREATED);

        this.activityRepository.save(activity);
    }
}
