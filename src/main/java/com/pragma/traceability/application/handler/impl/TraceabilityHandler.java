package com.pragma.traceability.application.handler.impl;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;
import com.pragma.traceability.application.handler.ITraceabilityHandler;
import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.RestaurantEfficiency;
import com.pragma.traceability.domain.model.Traceability;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ModelMapper modelMapper;

    @Override
    public TraceabilityResponseDto createTraceability(CreateTraceabilityRequestDto createTraceabilityRequestDto) {
        Traceability mappedTraceability = modelMapper.map(createTraceabilityRequestDto, Traceability.class);
        mappedTraceability.setDate(LocalDateTime.now());
        Traceability savedTraceability = traceabilityServicePort.createTraceability(mappedTraceability);

        return modelMapper.map(savedTraceability, TraceabilityResponseDto.class);
    }

    @Override
    public List<TraceabilityResponseDto> getOrderTraceability(Long orderId) {
        List<Traceability> traceabilityList = traceabilityServicePort.getOrderTraceability(orderId);
        return traceabilityList.stream()
                .map(traceability -> modelMapper.map(traceability, TraceabilityResponseDto.class))
                .toList();
    }

    @Override
    public List<RestaurantEfficiencyResponseDto> getRestaurantEfficiency(List<Long> orderIds) {
        return traceabilityServicePort.getRestaurantEfficiency(orderIds).stream()
                .map(restaurantEfficiency -> modelMapper.map(restaurantEfficiency, RestaurantEfficiencyResponseDto.class)).
                toList();
    }
}
