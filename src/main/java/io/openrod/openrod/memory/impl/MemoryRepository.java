package io.openrod.openrod.memory.impl;

import io.openrod.openrod.app.impl.App;
import io.openrod.openrod.memory.MemoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, UUID>, QuerydslPredicateExecutor<Memory> {

    @Query("SELECT m FROM Memory m WHERE m.deleted = false AND m.category.id IN :categories")
    List<Memory> findByCategoryIdIn(@Param("categories") List<UUID> categories);

    @Query("SELECT m FROM Memory m WHERE m.deleted = false")
    List<Memory> findAll();

    @Modifying
    @Query("UPDATE Memory m SET m.deleted = true WHERE m.id = :id")
    void softDeleteById(@Param("id") UUID id);

    @Query("SELECT m FROM Memory m WHERE m.deleted = false AND  m.category.id = :categoryId")
    List<Memory> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT m FROM Memory m JOIN m.tags t WHERE m.deleted = false AND t.id = :tagId")
    List<Memory> findByTagId(@Param("tagId") UUID tagId);

}
