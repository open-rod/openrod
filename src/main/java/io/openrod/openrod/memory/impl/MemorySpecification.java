package io.openrod.openrod.memory.impl;

import io.openrod.openrod.category.impl.Category;
import io.openrod.openrod.tag.impl.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

class MemorySpecification {
    public static Specification<Memory> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // Always true
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Memory> hasCategories(List<UUID> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Memory, Category> categoryJoin = root.join("categories", JoinType.LEFT);
            return categoryJoin.get("id").in(categories);
        };
    }

    public static Specification<Memory> hasTags(List<UUID> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Memory, Tag> tagJoin = root.join("tags", JoinType.LEFT);
            return tagJoin.get("id").in(tags);
        };
    }
}
