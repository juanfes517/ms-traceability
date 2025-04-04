package com.pragma.traceability.application.handler.impl;

import com.pragma.traceability.application.dto.request.CreateTraceabilityRequestDto;
import com.pragma.traceability.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.traceability.application.dto.response.TraceabilityResponseDto;
import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.RestaurantEfficiency;
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

    @Test
    void getRestaurantEfficiency_WhenIsSuccessful() {
        List<Long> orderIds = List.of(1L, 2L);

        RestaurantEfficiency restaurantEfficiency1 = RestaurantEfficiency.builder()
                .orderId(orderIds.get(0))
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiency restaurantEfficiency2 = RestaurantEfficiency.builder()
                .orderId(orderIds.get(0))
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiencyResponseDto restaurantEfficiencyDto1 = RestaurantEfficiencyResponseDto.builder()
                .orderId(orderIds.get(0))
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiencyResponseDto restaurantEfficiencyDto2 = RestaurantEfficiencyResponseDto.builder()
                .orderId(orderIds.get(0))
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        List<RestaurantEfficiency> restaurantEfficiencies = List.of(restaurantEfficiency1, restaurantEfficiency2);

        when(traceabilityServicePort.getRestaurantEfficiency(orderIds))
                .thenReturn(restaurantEfficiencies);
        when(modelMapper.map(restaurantEfficiency1, RestaurantEfficiencyResponseDto.class))
                .thenReturn(restaurantEfficiencyDto1);
        when(modelMapper.map(restaurantEfficiency2, RestaurantEfficiencyResponseDto.class))
                .thenReturn(restaurantEfficiencyDto2);

        List<RestaurantEfficiencyResponseDto> result = traceabilityHandler.getRestaurantEfficiency(orderIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(restaurantEfficiencyDto1.getOrderId(), result.get(0).getOrderId());
        assertEquals(restaurantEfficiencyDto1.getFinalStatus(), result.get(0).getFinalStatus());
        assertEquals(restaurantEfficiencyDto2.getOrderId(), result.get(1).getOrderId());
        assertEquals(restaurantEfficiencyDto2.getFinalStatus(), result.get(1).getFinalStatus());
    }

    @Test
    void getEmployeeEfficiency() {

        List<Long> employeeIds = List.of(1L, 2L);

        EmployeeEfficiency employeeEfficiency1 = EmployeeEfficiency.builder()
                .employeeId(employeeIds.get(0))
                .employeeEmail("employee1@mail.com")
                .averageProcessingTimeInSeconds(43)
                .build();

        EmployeeEfficiency employeeEfficiency2 = EmployeeEfficiency.builder()
                .employeeId(employeeIds.get(1))
                .employeeEmail("employee2@mail.com")
                .averageProcessingTimeInSeconds(34)
                .build();

        EmployeeEfficiencyResponseDto employeeEfficiencyDto1 = EmployeeEfficiencyResponseDto.builder()
                .employeeId(employeeIds.get(0))
                .employeeEmail("employee1@mail.com")
                .averageProcessingTimeInSeconds(43)
                .build();

        EmployeeEfficiencyResponseDto employeeEfficiencyDto2 = EmployeeEfficiencyResponseDto.builder()
                .employeeId(employeeIds.get(1))
                .employeeEmail("employee2@mail.com")
                .averageProcessingTimeInSeconds(34)
                .build();

        List<EmployeeEfficiency> employees = List.of(employeeEfficiency1, employeeEfficiency2);

        when(traceabilityServicePort.getEmployeeEfficiency(employeeIds))
                .thenReturn(employees);
        when( modelMapper.map(employeeEfficiency1, EmployeeEfficiencyResponseDto.class))
                .thenReturn(employeeEfficiencyDto1);
        when( modelMapper.map(employeeEfficiency2, EmployeeEfficiencyResponseDto.class))
                .thenReturn(employeeEfficiencyDto2);

        List<EmployeeEfficiencyResponseDto> result = traceabilityHandler.getEmployeeEfficiency(employeeIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(employeeEfficiencyDto1.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(employeeEfficiencyDto2.getEmployeeId(), result.get(1).getEmployeeId());
        assertEquals(employeeEfficiencyDto1.getEmployeeEmail(), result.get(0).getEmployeeEmail());
        assertEquals(employeeEfficiencyDto2.getEmployeeEmail(), result.get(1).getEmployeeEmail());
    }
}