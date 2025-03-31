package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import com.pragma.traceability.domain.helper.constants.ExceptionConstants;
import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;
    private final IJwtSecurityServicePort jwtSecurityServicePort;

    @Override
    public Traceability createTraceability(Traceability traceability) {
        return traceabilityPersistencePort.save(traceability);
    }

    @Override
    public List<Traceability> getOrderTraceability(Long orderId) {
        List<Traceability> traceability = traceabilityPersistencePort.findAllByOrderId(orderId);
        String tokenEmail = jwtSecurityServicePort.getSubject();
        String customerEmail = traceability.get(0).getCustomerEmail();

        this.validateCustomerEmail(customerEmail, tokenEmail);

        return traceability;
    }

    private void validateCustomerEmail(String customerEmail, String tokenEmail) {
        if (!customerEmail.equals(tokenEmail)) {
            throw new OrderNotFromCustomerException(ExceptionConstants.ORDER_NOT_CREATED_BY_THE_CUSTOMER);
        }
    }
}
