package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import com.pragma.traceability.domain.helper.constants.ExceptionConstants;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.RestaurantEfficiency;
import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityUseCaseTest {

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @Mock
    private ITraceabilityPersistencePort traceabilityPersistencePort;

    @Mock
    private IJwtSecurityServicePort jwtSecurityServicePort;

    @Test
    void createTraceability_WhenIsSuccessful() {

        LocalDateTime date = LocalDateTime.now();

        Traceability traceability = Traceability.builder()
                .id(null)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(date)
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
                .date(date)
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        when(traceabilityPersistencePort.save(traceability))
                .thenReturn(savedTraceability);

        Traceability result = traceabilityUseCase.createTraceability(traceability);

        assertNotNull(result);
        assertEquals(savedTraceability.getId(), result.getId());
        assertEquals(savedTraceability.getOrderId(), result.getOrderId());
        assertEquals(savedTraceability.getCustomerId(), result.getCustomerId());
        assertEquals(savedTraceability.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(savedTraceability.getDate(), result.getDate());
        assertEquals(savedTraceability.getPreviousStatus(), result.getPreviousStatus());
        assertEquals(savedTraceability.getNewStatus(), result.getNewStatus());
        assertEquals(savedTraceability.getEmployeeId(), result.getEmployeeId());
        assertEquals(savedTraceability.getEmployeeEmail(), result.getEmployeeEmail());
    }

    @Test
    void getOrderTraceability_whenIsSuccessful() {
        Long orderId = 1L;
        String tokenEmail = "customer@mail.com";

        Traceability traceability = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail(tokenEmail)
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceabilityList = List.of(traceability);

        when(traceabilityPersistencePort.findAllByOrderId(orderId))
                .thenReturn(traceabilityList);
        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);

        List<Traceability> result = traceabilityUseCase.getOrderTraceability(orderId);

        assertNotNull(result);
        assertEquals(traceabilityList.size(), result.size());
        assertEquals(traceabilityList.get(0).getId(), result.get(0).getId());
        assertEquals(traceabilityList.get(0).getOrderId(), result.get(0).getOrderId());
        assertEquals(traceabilityList.get(0).getCustomerId(), result.get(0).getCustomerId());
        assertEquals(traceabilityList.get(0).getCustomerEmail(), result.get(0).getCustomerEmail());
        assertEquals(traceabilityList.get(0).getDate(), result.get(0).getDate());
        assertEquals(traceabilityList.get(0).getPreviousStatus(), result.get(0).getPreviousStatus());
        assertEquals(traceabilityList.get(0).getNewStatus(), result.get(0).getNewStatus());
        assertEquals(traceabilityList.get(0).getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(traceabilityList.get(0).getEmployeeEmail(), result.get(0).getEmployeeEmail());
    }

    @Test
    void getOrderTraceability_whenThrowOrderNotFromCustomerException() {
        Long orderId = 1L;
        String tokenEmail = "customer@mail.com";

        Traceability traceability = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("other@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceabilityList = List.of(traceability);

        when(traceabilityPersistencePort.findAllByOrderId(orderId))
                .thenReturn(traceabilityList);
        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);

        OrderNotFromCustomerException result = assertThrows(OrderNotFromCustomerException.class, () ->
                traceabilityUseCase.getOrderTraceability(orderId));

        assertNotNull(result);
        assertEquals(ExceptionConstants.ORDER_NOT_CREATED_BY_THE_CUSTOMER, result.getMessage());
    }

    @Test
    void getRestaurantEfficiency_WhenIsSuccessful() {
        Long orderId = 1L;
        List<Long> orderIds = List.of(orderId);

        Traceability traceabilityPending = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus(null)
                .newStatus("PENDING")
                .employeeId(null)
                .employeeEmail(null)
                .build();

        Traceability traceabilityPreparing = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PENDING")
                .newStatus("PREPARING")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        Traceability traceabilityReady = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PREPARING")
                .newStatus("READY")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        Traceability traceabilityDelivered = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("READY")
                .newStatus("DELIVERED")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceability = List.of(traceabilityPending, traceabilityPreparing, traceabilityReady, traceabilityDelivered);

        when(traceabilityPersistencePort.findAllByOrderId(orderId))
                .thenReturn(traceability);

        List<RestaurantEfficiency> result = traceabilityUseCase.getRestaurantEfficiency(orderIds);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(orderId, result.get(0).getOrderId());
    }

    @Test
    void getRestaurantEfficiency_WhenTheOrderIsNotComplete() {
        Long orderId = 1L;
        List<Long> orderIds = List.of(orderId);

        Traceability traceabilityPending = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus(null)
                .newStatus("PENDING")
                .employeeId(null)
                .employeeEmail(null)
                .build();

        Traceability traceabilityPreparing = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PENDING")
                .newStatus("PREPARING")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        Traceability traceabilityReady = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PREPARING")
                .newStatus("READY")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceability = List.of(traceabilityPending, traceabilityPreparing, traceabilityReady);

        when(traceabilityPersistencePort.findAllByOrderId(orderId))
                .thenReturn(traceability);

        List<RestaurantEfficiency> result = traceabilityUseCase.getRestaurantEfficiency(orderIds);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getEmployeeEfficiency_WhenIsSuccessful() {

        Long employeeId = 1L;
        String employeeEmail = "employee@mail.com";
        List<Long> employeeIds = List.of(employeeId);

        Traceability traceabilityPreparing1 = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PENDING")
                .newStatus("PREPARING")
                .employeeId(employeeId)
                .employeeEmail(employeeEmail)
                .build();

        Traceability traceabilityPreparing2 = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PENDING")
                .newStatus("PREPARING")
                .employeeId(employeeId)
                .employeeEmail(employeeEmail)
                .build();

        Traceability traceabilityDelivered1 = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("READY")
                .newStatus("DELIVERED")
                .employeeId(employeeId)
                .employeeEmail(employeeEmail)
                .build();

        Traceability traceabilityDelivered2 = Traceability.builder()
                .id(1L)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("READY")
                .newStatus("DELIVERED")
                .employeeId(employeeId)
                .employeeEmail(employeeEmail)
                .build();

        List<Traceability> traceabilityPreparing = List.of(traceabilityPreparing1, traceabilityPreparing2);
        List<Traceability> traceabilityDelivered = List.of(traceabilityDelivered1, traceabilityDelivered2);

        when(traceabilityPersistencePort.findAllByEmployeeIdAndNewStatus(employeeId, "PREPARING"))
                .thenReturn(traceabilityPreparing);
        when(traceabilityPersistencePort.findAllByEmployeeIdAndNewStatus(employeeId, "DELIVERED"))
                .thenReturn(traceabilityDelivered);

        List<EmployeeEfficiency> result = traceabilityUseCase.getEmployeeEfficiency(employeeIds);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}