package io.openrod.openrod.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemorySearchCriteria {

    private String name;

    private List<UUID> tags = new ArrayList<>();

    private List<UUID> categories = new ArrayList<>();

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTags(final List<UUID> tags) {
        this.tags = tags;
    }

    public List<UUID> getTags() {
        return this.tags;
    }

    public void setCategories(final List<UUID> categories) {
        this.categories = categories;
    }

    public List<UUID> getCategories() {
        return this.categories;
    }
}
