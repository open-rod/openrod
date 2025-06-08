package io.openrod.openrod.activity;

import io.openrod.openrod.activity.impl.ActivityType;
import io.openrod.openrod.memory.MemoryDTO;

public class MemoryActivityDTO extends ActivityDTO {

    private MemoryDTO memory;

    private MemoryActivityAction action;

    @Override
    public ActivityType getType() {
        return ActivityType.MEMORY;
    }

    public void setMemory(final MemoryDTO memory) {
        this.memory = memory;
    }

    public MemoryDTO getMemory() {
        return this.memory;
    }

    public void setAction(final MemoryActivityAction action) {
        this.action = action;
    }

    public MemoryActivityAction getAction() {
        return this.action;
    }
}
