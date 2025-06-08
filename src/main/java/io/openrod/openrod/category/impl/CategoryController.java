package io.openrod.openrod.category.impl;

import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.category.CategoryInfoDTO;
import io.openrod.openrod.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(
        final CategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryInfoDTO>> getCategories() {
        return ResponseEntity.ok(
            this.categoryService.getCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(
        @PathVariable("id") final UUID id
    ) {
        return ResponseEntity.ok(
            this.categoryService.getCategory(id)
        );
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(
        @RequestBody final CategoryDTO category
    ) {
        var createdCategory = this.categoryService.createCategory(category);

        var location = fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdCategory.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
        @PathVariable("id") final UUID id,
        @RequestBody final CategoryDTO category
    ) {
        category.setId(id);

        return ResponseEntity.accepted()
            .body(
                this.categoryService.updateCategory(category)
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
        @PathVariable("id") final UUID id
    ) {
        this.categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
