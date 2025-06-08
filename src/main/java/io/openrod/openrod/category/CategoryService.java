package io.openrod.openrod.category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryInfoDTO> getCategories();
    CategoryDTO getCategory(final UUID id);
    CategoryDTO createCategory(final CategoryDTO category);
    CategoryDTO updateCategory(final CategoryDTO category);
    void deleteCategory(final UUID id);
}
