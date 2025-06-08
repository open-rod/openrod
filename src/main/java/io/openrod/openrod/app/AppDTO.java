package io.openrod.openrod.app;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.common.dto.BaseDTO;
import io.openrod.openrod.configuration.date.LocalDateTimeDeserializer;
import io.openrod.openrod.configuration.date.LocalDateTimeSerializer;
import io.openrod.openrod.tag.TagDTO;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AppDTO extends BaseDTO {

    private String title;

    private String description;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastActive;

    private AppStatus status;

    private AppType type;

    private String apiKey;

    private Set<CategoryDTO> categories = new HashSet<>();

    private Set<TagDTO> tags = new HashSet<>();

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

    public void setCategories(final Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public void addCategory(final CategoryDTO category) {
        if (Objects.isNull(this.categories)) {
            this.categories = new HashSet<>();
        }
        this.categories.add(category);
    }

    public Set<CategoryDTO> getCategories() {
        return this.categories;
    }

    public void setTags(final Set<TagDTO> tags) {
        this.tags = tags;
    }

    public void addTag(final TagDTO tag) {
        if (Objects.isNull(this.tags)) {
            this.tags = new HashSet<>();
        }
        this.tags.add(tag);
    }

    public Set<TagDTO> getTags() {
        return this.tags;
    }
}
