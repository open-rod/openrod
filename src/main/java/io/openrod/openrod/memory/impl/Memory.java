package io.openrod.openrod.memory.impl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.openrod.openrod.category.impl.Category;
import io.openrod.openrod.common.entity.SoftDeletableEntity;
import io.openrod.openrod.memory.MemoryStatus;
import io.openrod.openrod.memory.MemoryType;
import io.openrod.openrod.tag.impl.Tag;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "memories")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "memory_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FileMemory.class, name = "file"),
    @JsonSubTypes.Type(value = ContentMemory.class, name = "content")
})
public abstract class Memory extends SoftDeletableEntity {

    @Column()
    private String title;

    @Column()
    private String description;

    @Column()
    @Enumerated(EnumType.STRING)
    private MemoryStatus status;

    @ManyToMany()
    @JoinTable(
        name = "memory_tags",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "memory_id")
    )
    private Set<Tag> tags;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "added_by")
    private String addedBy;

    @Column(name = "added_through")
    private String addedThrough;

    @Column(name = "added_through_id")
    private UUID addedThroughId;

    public abstract MemoryType getMemoryType();

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStatus(final MemoryStatus status) {
        this.status = status;
    }

    public MemoryStatus getStatus() {
        return this.status;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedBy() {
        return this.addedBy;
    }

    public void setAddedThrough(final String addedThrough) {
        this.addedThrough = addedThrough;
    }

    public String getAddedThrough() {
        return this.addedThrough;
    }

    public void setAddedThroughId(final UUID addedThroughId) {
        this.addedThroughId = addedThroughId;
    }

    public UUID getAddedThroughId() {
        return this.addedThroughId;
    }
}
