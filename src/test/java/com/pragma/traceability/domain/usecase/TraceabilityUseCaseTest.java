package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import com.pragma.traceability.domain.helper.constants.ExceptionConstants;
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
}