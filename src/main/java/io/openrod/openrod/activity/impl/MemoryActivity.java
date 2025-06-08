package io.openrod.openrod.activity.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.openrod.openrod.activity.MemoryActivityAction;
import io.openrod.openrod.memory.impl.Memory;
import jakarta.persistence.*;

@Entity()
@DiscriminatorValue("MEMORY")
@JsonTypeName("memory")
public class MemoryActivity extends Activity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "memory_id", referencedColumnName = "id")
    private Memory memory;

    @Column(name = "memory_action")
    @Enumerated(EnumType.STRING)
    private MemoryActivityAction action;

    @Override
    ActivityType getType() {
        return ActivityType.MEMORY;
    }

    public void setMemory(final Memory memory) {
        this.memory = memory;
    }

    public Memory getMemory() {
        return this.memory;
    }

    public void setAction(final MemoryActivityAction action) {
        this.action = action;
    }

    public MemoryActivityAction getAction() {
        return this.action;
    }
}
