package com.pragma.traceability.infrastructure.out.mongo.adapter;

import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability.infrastructure.out.mongo.collection.TraceabilityCollection;
import com.pragma.traceability.infrastructure.out.mongo.repository.TraceabilityRepository;
import com.pragma.traceability.infrastructure.out.mongo.sequence.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final TraceabilityRepository traceabilityRepository;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Traceability save(Traceability traceability) {
        TraceabilityCollection mappedTraceabilityCollection = modelMapper.map(traceability, TraceabilityCollection.class);
        Long id = sequenceGeneratorService.generateSequence(TraceabilityCollection.SEQUENCE_NAME);

        mappedTraceabilityCollection.setId(id);
        TraceabilityCollection savedTraceabilityCollection = traceabilityRepository.save(mappedTraceabilityCollection);

        return modelMapper.map(savedTraceabilityCollection, Traceability.class);
    }

    @Override
    public List<Traceability> findAllByOrderId(Long orderId) {
        List<TraceabilityCollection> traceabilityCollections = traceabilityRepository.findAllByOrderId(orderId);

        return traceabilityCollections.stream()
                .map(traceabilityCollection -> modelMapper.map(traceabilityCollection, Traceability.class))
                .toList();
    }

    @Override
    public List<Traceability> findAllByEmployeeIdAndNewStatus(Long employeeId, String newStatus) {
        List<TraceabilityCollection> traceabilityCollections = traceabilityRepository.findAllByEmployeeIdAndNewStatus(employeeId, newStatus);

        return traceabilityCollections.stream()
                .map(traceabilityCollection -> modelMapper.map(traceabilityCollection, Traceability.class))
                .toList();
    }
}
