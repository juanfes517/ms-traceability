package com.pragma.traceability.application.handler;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    TraceabilityResponseDto createTraceability(CreateTraceabilityRequestDto createTraceabilityRequestDto);

    List<TraceabilityResponseDto> getOrderTraceability(Long orderId);

    List<RestaurantEfficiencyResponseDto> getRestaurantEfficiency(List<Long> orderIds);

    List<EmployeeEfficiencyResponseDto> getEmployeeEfficiency(List<Long> employeeIds);
}
