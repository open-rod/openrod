package io.openrod.openrod.memory;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.common.dto.BaseDTO;
import io.openrod.openrod.tag.TagDTO;

import java.util.List;
import java.util.UUID;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FileMemoryDTO.class, name = "FILE"),
    @JsonSubTypes.Type(value = ContentMemoryDTO.class, name = "CONTENT")
})
public abstract class MemoryDTO extends BaseDTO {

    private String title;
    private String description;
    private MemoryStatus status;
    private List<TagDTO> tags;
    private CategoryDTO category;
    private String addedBy;
    private String addedThrough;
    private UUID addedThroughId;

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

    public abstract MemoryType getType();

    public void setStatus(final MemoryStatus status) {
        this.status = status;
    }

    public MemoryStatus getStatus() {
        return this.status;
    }

    public void setTags(final List<TagDTO> tags) {
        this.tags = tags;
    }

    public List<TagDTO> getTags() {
        return this.tags;
    }

    public void setCategory(final CategoryDTO category) {
        this.category = category;
    }

    public CategoryDTO getCategory() {
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
