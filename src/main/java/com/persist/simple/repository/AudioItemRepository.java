package com.persist.simple.repository;

import com.persist.simple.domain.AudioItem;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the AudioItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AudioItemRepository extends CassandraRepository<AudioItem, UUID> {}
