package com.pragma.traceability.infrastructure.out.mongo.adapter;

import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability.infrastructure.out.mongo.collection.TraceabilityCollection;
import com.pragma.traceability.infrastructure.out.mongo.repository.TraceabilityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TraceabilityMongoAdapter implements ITraceabilityPersistencePort {

    private final TraceabilityRepository traceabilityRepository;
    private final ModelMapper modelMapper;

    @Override
    public Traceability save(Traceability traceability) {
        TraceabilityCollection mappedTraceabilityCollection = modelMapper.map(traceability, TraceabilityCollection.class);
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
}
