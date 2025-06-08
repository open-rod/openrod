package io.openrod.openrod.app.events;

import io.openrod.openrod.app.impl.App;

public class AppCreatedEvent extends AppEvent {

    public AppCreatedEvent(final App app) {
        super(app);
    }

}
