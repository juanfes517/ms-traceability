package com.pragma.traceability.application.handler;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    TraceabilityResponseDto createTraceability(CreateTraceabilityRequestDto createTraceabilityRequestDto);

    List<TraceabilityResponseDto> getOrderTraceability(Long orderId);
}
