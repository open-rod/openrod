package io.openrod.openrod.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(
        final ActivityService activityService
    ) {
        this.activityService = activityService;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ActivityDTO>> getRecentActivities() {
        return ResponseEntity.ok(
            this.activityService.getRecentActivities()
        );
    }

    @GetMapping("/app/recent")
    public ResponseEntity<List<ActivityDTO>> getRecentAppActivities() {
        return ResponseEntity.ok(
            this.activityService.getRecentAppActivities()
        );
    }

    @GetMapping("/memory/recent")
    public ResponseEntity<List<ActivityDTO>> getRecentMemoryActivities() {
        return ResponseEntity.ok(
            this.activityService.getRecentMemoryActivities()
        );
    }

}
