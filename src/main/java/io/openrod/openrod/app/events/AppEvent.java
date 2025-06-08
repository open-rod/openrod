package io.openrod.openrod.app.events;

import io.openrod.openrod.app.impl.App;

public abstract class AppEvent {

    private final App app;

    public AppEvent(final App app) {
        this.app = app;
    }

    public App getApp() {
        return this.app;
    }
}
