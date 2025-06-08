package io.openrod.openrod.activity.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.openrod.openrod.activity.AppActivityAction;
import io.openrod.openrod.app.impl.App;
import jakarta.persistence.*;

@Entity()
@DiscriminatorValue("APP")
@JsonTypeName("app")
public class AppActivity extends Activity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_id", referencedColumnName = "id")
    private App app;

    @Column(name = "app_action")
    @Enumerated(EnumType.STRING)
    private AppActivityAction action;

    @Override
    ActivityType getType() {
        return ActivityType.APP;
    }

    public void setApp(final App app) {
        this.app = app;
    }

    public App getApp() {
        return this.app;
    }

    public void setAction(final AppActivityAction action) {
        this.action = action;
    }

    public AppActivityAction getAction() {
        return this.action;
    }
}
