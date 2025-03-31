package com.pragma.traceability.infrastructure.configuration;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import com.pragma.traceability.domain.usecase.TraceabilityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ITraceabilityServicePort traceabilityService(ITraceabilityPersistencePort traceabilityPersistencePort, IJwtSecurityServicePort jwtSecurityServicePort) {
        return new TraceabilityUseCase(traceabilityPersistencePort, jwtSecurityServicePort);
    }
}
