package com.pragma.traceability.application.handler.impl;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;
import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.Traceability;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityHandlerTest {

    @InjectMocks
    private TraceabilityHandler traceabilityHandler;

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void createTraceability_WhenIsSuccessful() {
        CreateTraceabilityRequestDto createTraceabilityRequestDto = CreateTraceabilityRequestDto.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@email.com")
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@email.com")
                .build();

        Traceability mappedTraceability = Traceability.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        Traceability savedTraceability = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        TraceabilityResponseDto responseTraceability = TraceabilityResponseDto.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        when(modelMapper.map(createTraceabilityRequestDto, Traceability.class))
                .thenReturn(mappedTraceability);
        when(traceabilityServicePort.createTraceability(mappedTraceability))
                .thenReturn(savedTraceability);
        when(modelMapper.map(savedTraceability, TraceabilityResponseDto.class))
                .thenReturn(responseTraceability);

        TraceabilityResponseDto result = traceabilityHandler.createTraceability(createTraceabilityRequestDto);

        assertNotNull(result);
        assertEquals(responseTraceability.getOrderId(), result.getOrderId());
        assertEquals(responseTraceability.getCustomerId(), result.getCustomerId());
        assertEquals(responseTraceability.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(responseTraceability.getDate(), result.getDate());
        assertEquals(responseTraceability.getPreviousStatus(), result.getPreviousStatus());
        assertEquals(responseTraceability.getNewStatus(), result.getNewStatus());
        assertEquals(responseTraceability.getEmployeeId(), result.getEmployeeId());
        assertEquals(responseTraceability.getEmployeeEmail(), result.getEmployeeEmail());
    }

    @Test
    void getOrderTraceability_WhenIsSuccessful() {
        Long orderId = 1L;
        Traceability savedTraceability = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        TraceabilityResponseDto responseTraceability = TraceabilityResponseDto.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceabilityList = List.of(savedTraceability);

        when(traceabilityServicePort.getOrderTraceability(orderId))
                .thenReturn(traceabilityList);
        when(modelMapper.map(savedTraceability, TraceabilityResponseDto.class))
                .thenReturn(responseTraceability);

        List<TraceabilityResponseDto> result = traceabilityHandler.getOrderTraceability(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTraceability.getOrderId(), result.get(0).getOrderId());
        assertEquals(responseTraceability.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(responseTraceability.getCustomerEmail(), result.get(0).getCustomerEmail());
        assertEquals(responseTraceability.getDate(), result.get(0).getDate());
        assertEquals(responseTraceability.getPreviousStatus(), result.get(0).getPreviousStatus());
        assertEquals(responseTraceability.getNewStatus(), result.get(0).getNewStatus());
        assertEquals(responseTraceability.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(responseTraceability.getEmployeeEmail(), result.get(0).getEmployeeEmail());

    }
}