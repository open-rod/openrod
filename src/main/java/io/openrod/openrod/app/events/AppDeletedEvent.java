package io.openrod.openrod.app.events;

import io.openrod.openrod.app.impl.App;

public class AppDeletedEvent extends AppEvent {

    public AppDeletedEvent(final App app) {
        super(app);
    }
}
