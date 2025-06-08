package io.openrod.openrod.activity.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.openrod.openrod.activity.Severity;
import io.openrod.openrod.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AppActivity.class, name = "app"),
    @JsonSubTypes.Type(value = MemoryActivity.class, name = "memory")
})
public abstract class Activity extends BaseEntity {

    @Column()
    @Enumerated(EnumType.STRING)
    private Severity severity;

    abstract ActivityType getType();

    public void setSeverity(final Severity severity) {
        this.severity = severity;
    }

    public Severity getSeverity() {
        return this.severity;
    }
}
