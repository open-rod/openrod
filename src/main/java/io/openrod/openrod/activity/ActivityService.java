package io.openrod.openrod.activity;

import java.util.List;

public interface ActivityService {

    List<ActivityDTO> getRecentActivities();

    List<ActivityDTO> getRecentAppActivities();

    List<ActivityDTO> getRecentMemoryActivities();

}
