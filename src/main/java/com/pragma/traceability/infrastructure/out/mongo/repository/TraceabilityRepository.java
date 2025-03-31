package com.pragma.traceability.infrastructure.out.mongo.repository;

import com.pragma.traceability.infrastructure.out.mongo.collection.TraceabilityCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TraceabilityRepository extends MongoRepository<TraceabilityCollection, Long> {

    List<TraceabilityCollection> findAllByOrderId(Long orderId);
}
