package com.persist.simple.repository;

import com.persist.simple.domain.Category;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends CassandraRepository<Category, UUID> {}
