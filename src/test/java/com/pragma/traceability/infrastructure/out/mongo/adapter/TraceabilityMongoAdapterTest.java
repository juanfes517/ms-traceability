package com.pragma.traceability.infrastructure.out.mongo.adapter;

import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.infrastructure.out.mongo.collection.TraceabilityCollection;
import com.pragma.traceability.infrastructure.out.mongo.repository.TraceabilityRepository;
import com.pragma.traceability.infrastructure.out.mongo.sequence.SequenceGeneratorService;
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
class TraceabilityMongoAdapterTest {

    @InjectMocks
    private TraceabilityMongoAdapter traceabilityMongoAdapter;

    @Mock
    private TraceabilityRepository traceabilityRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SequenceGeneratorService sequenceGeneratorService;

    @Test
    void save_whenIsSuccessful() {

        Long autoGeneratedId = 1L;

        Traceability traceability = Traceability.builder()
                .id(null)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        TraceabilityCollection mappedTraceabilityCollection = TraceabilityCollection.builder()
                .id(null)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        TraceabilityCollection savedTraceabilityCollection = TraceabilityCollection.builder()
                .id(autoGeneratedId)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        Traceability mappedTraceability = Traceability.builder()
                .id(autoGeneratedId)
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        when(modelMapper.map(traceability, TraceabilityCollection.class))
                .thenReturn(mappedTraceabilityCollection);
        when(traceabilityRepository.save(mappedTraceabilityCollection))
                .thenReturn(savedTraceabilityCollection);
        when(modelMapper.map(savedTraceabilityCollection, Traceability.class))
                .thenReturn(mappedTraceability);
        when(sequenceGeneratorService.generateSequence(TraceabilityCollection.SEQUENCE_NAME))
                .thenReturn(autoGeneratedId);

        Traceability result = traceabilityMongoAdapter.save(traceability);

        assertNotNull(result);
        assertEquals(mappedTraceability.getId(), result.getId());
        assertEquals(mappedTraceability.getOrderId(), result.getOrderId());
        assertEquals(mappedTraceability.getCustomerId(), result.getCustomerId());
        assertEquals(mappedTraceability.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(mappedTraceability.getDate(), result.getDate());
        assertEquals(mappedTraceability.getPreviousStatus(), result.getPreviousStatus());
        assertEquals(mappedTraceability.getNewStatus(), result.getNewStatus());
        assertEquals(mappedTraceability.getEmployeeId(), result.getEmployeeId());
        assertEquals(mappedTraceability.getEmployeeEmail(), result.getEmployeeEmail());
    }

    @Test
    void findAllByOrderId_WhenIsSuccessful() {
        Long orderId = 1L;
        LocalDateTime date = LocalDateTime.now();

        TraceabilityCollection traceabilityCollection1 = TraceabilityCollection.builder()
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

        Traceability traceability1 = Traceability.builder()
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

        List<TraceabilityCollection> traceabilityCollections = List.of(traceabilityCollection1);

        when(traceabilityRepository.findAllByOrderId(orderId))
                .thenReturn(traceabilityCollections);
        when(modelMapper.map(traceabilityCollection1, Traceability.class))
                .thenReturn(traceability1);

        List<Traceability> result = traceabilityMongoAdapter.findAllByOrderId(orderId);

        assertNotNull(result);
        assertEquals(traceabilityCollections.size(), result.size());
        assertEquals(traceabilityCollections.get(0).getId(), result.get(0).getId());
        assertEquals(traceabilityCollections.get(0).getOrderId(), result.get(0).getOrderId());
        assertEquals(traceabilityCollections.get(0).getCustomerId(), result.get(0).getCustomerId());
        assertEquals(traceabilityCollections.get(0).getCustomerEmail(), result.get(0).getCustomerEmail());
        assertEquals(traceabilityCollections.get(0).getDate(), result.get(0).getDate());
        assertEquals(traceabilityCollections.get(0).getPreviousStatus(), result.get(0).getPreviousStatus());
        assertEquals(traceabilityCollections.get(0).getNewStatus(), result.get(0).getNewStatus());
        assertEquals(traceabilityCollections.get(0).getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(traceabilityCollections.get(0).getEmployeeEmail(), result.get(0).getEmployeeEmail());
    }
}