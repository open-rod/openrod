package io.openrod.openrod.app.impl;

import io.openrod.openrod.app.AppStatus;
import io.openrod.openrod.app.AppType;
import io.openrod.openrod.category.impl.Category;
import io.openrod.openrod.common.entity.BaseEntity;
import io.openrod.openrod.common.entity.SoftDeletableEntity;
import io.openrod.openrod.tag.impl.Tag;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity()
@Table(name = "apps")
public class App extends SoftDeletableEntity {

    @Column()
    private String title;

    @Column()
    private String description;

    @Column()
    private LocalDateTime lastActive;

    @Column()
    @Enumerated(EnumType.STRING)
    private AppStatus status;

    @Column()
    @Enumerated(EnumType.STRING)
    private AppType type;

    @Column(name = "api_key")
    private String apiKey;

    @ManyToMany()
    @JoinTable(
        name = "app_categories",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private Set<Category> categories = new HashSet<>();

    @ManyToMany()
    @JoinTable(
        name = "app_tags",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private Set<Tag> tags;

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

    public void setLastActive(final LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public LocalDateTime getLastActive() {
        return this.lastActive;
    }

    public void setStatus(final AppStatus status) {
        this.status = status;
    }

    public AppStatus getStatus() {
        return this.status;
    }

    public void setType(final AppType type) {
        this.type = type;
    }

    public AppType getType() {
        return this.type;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }
    
    public void setCategories(final Set<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(final Category category) {
        if (Objects.isNull(this.categories)) {
            this.categories = new HashSet<>();
        }
        this.categories.add(category);
    }
    
    public Set<Category> getCategories() {
        return this.categories;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }
}
