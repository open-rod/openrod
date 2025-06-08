package io.openrod.openrod.app.impl;

import io.openrod.openrod.memory.impl.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface AppRepository extends JpaRepository<App, UUID> {

    @Query("SELECT a From App a WHERE a.apiKey = :apiKey AND a.deleted = false")
    Optional<App> findOneByApiKey(@Param("apiKey") final String apiKey);

    @Query("SELECT a FROM App a WHERE a.deleted = false")
    List<App> findAll();

    @Query("SELECT a FROM App a WHERE a.id = :id")
    Optional<App> findById(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE App a SET a.deleted = true WHERE a.id = :id")
    void softDeleteById(@Param("id") UUID id);

    @Query("SELECT a FROM App a JOIN a.categories c WHERE a.deleted = false AND c.id = :categoryId")
    List<App> findByCategoryId(@Param("categoryId") UUID categoryId);

    @Query("SELECT a FROM App a JOIN a.tags t WHERE a.deleted = false AND t.id = :tagId")
    List<App> findByTagId(@Param("tagId") UUID tagId);

}
