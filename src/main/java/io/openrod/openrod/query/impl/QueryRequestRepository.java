package io.openrod.openrod.query.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QueryRequestRepository extends JpaRepository<QueryRequest, UUID>, QuerydslPredicateExecutor<QueryRequest> {

    List<QueryRequest> findAllByAppId(final UUID appId);

}
