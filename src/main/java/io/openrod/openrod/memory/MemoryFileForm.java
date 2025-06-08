package io.openrod.openrod.memory;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class MemoryFileForm {

    @NotNull(message = "File is required")
    private MultipartFile file;

    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private List<String> tags;

    private UUID categoryId;

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCategoryId(final UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }
}

