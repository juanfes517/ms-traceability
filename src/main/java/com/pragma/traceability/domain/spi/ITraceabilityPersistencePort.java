package com.pragma.traceability.domain.spi;

import com.pragma.traceability.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityPersistencePort {

    Traceability save(Traceability traceability);

    List<Traceability> findAllByOrderId(Long orderId);
}
