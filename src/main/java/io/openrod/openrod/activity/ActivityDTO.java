package io.openrod.openrod.activity;

import io.openrod.openrod.activity.impl.ActivityType;
import io.openrod.openrod.common.dto.BaseDTO;

public abstract class ActivityDTO extends BaseDTO {

    private Severity severity;

    abstract ActivityType getType();

    public void setSeverity(final Severity severity) {
        this.severity = severity;
    }

    public Severity getSeverity() {
        return this.severity;
    }
}
