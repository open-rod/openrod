package io.openrod.openrod.activity.impl;

import io.openrod.openrod.memory.impl.Memory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    @Query("SELECT a FROM Activity a WHERE TYPE(a) = :entityType")
    List<Activity> findByEntityType(@Param("entityType") Class<? extends Activity> entityType, Pageable pageable);

}
