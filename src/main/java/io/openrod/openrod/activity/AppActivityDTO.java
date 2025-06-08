package io.openrod.openrod.activity;

import io.openrod.openrod.activity.impl.ActivityType;
import io.openrod.openrod.app.AppDTO;

public class AppActivityDTO extends ActivityDTO {

    private AppDTO app;

    private AppActivityAction action;

    @Override
    public ActivityType getType() {
        return ActivityType.APP;
    }

    public void setApp(final AppDTO app) {
        this.app = app;
    }

    public AppDTO getApp() {
        return this.app;
    }

    public void setAction(final AppActivityAction action) {
        this.action = action;
    }

    public AppActivityAction getAction() {
        return this.action;
    }
}
