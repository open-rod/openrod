package io.openrod.openrod.category;

public class CategoryInfoDTO {

    private final CategoryDTO category;
    private final int numberOfApps;
    private final int numberOfMemories;

    public CategoryInfoDTO(
        final CategoryDTO category,
        final int numberOfApps,
        final int numberOfMemories
    ) {
        this.category = category;
        this.numberOfApps = numberOfApps;
        this.numberOfMemories = numberOfMemories;
    }

    public CategoryDTO getCategory() {
        return this.category;
    }

    public int getNumberOfApps() {
        return this.numberOfApps;
    }

    public int getNumberOfMemories() {
        return this.numberOfMemories;
    }
}
