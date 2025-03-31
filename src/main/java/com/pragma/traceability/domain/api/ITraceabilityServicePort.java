package com.pragma.traceability.domain.api;

import com.pragma.traceability.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityServicePort {

    Traceability createTraceability(Traceability traceability);

    List<Traceability> getOrderTraceability(Long orderId);
}
