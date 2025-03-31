package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import com.pragma.traceability.domain.helper.constants.ExceptionConstants;
import com.pragma.traceability.domain.model.RestaurantEfficiency;
import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
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

    @Override
    public List<RestaurantEfficiency> getRestaurantEfficiency(List<Long> orderIds) {
        List<RestaurantEfficiency> restaurantEfficiencies = new ArrayList<>();

        for (Long orderId : orderIds) {
            List<Traceability> traceability = traceabilityPersistencePort.findAllByOrderId(orderId);
            RestaurantEfficiency restaurantEfficiency = this.createRestaurantEfficiency(traceability);

            if (restaurantEfficiency != null) {
                restaurantEfficiencies.add(restaurantEfficiency);
            }
        }

        return restaurantEfficiencies;
    }

    private RestaurantEfficiency createRestaurantEfficiency(List<Traceability> traceability) {

        Traceability firstTraceability = this.obtainFirstTraceabilityOfTheOrder(traceability);
        Traceability finalTraceability = this.obtainFinalTraceabilityOfTheOrder(traceability);

        if (firstTraceability == null || finalTraceability == null) {
            return null;
        }

        Duration orderDuration = Duration.between(firstTraceability.getDate(), finalTraceability.getDate());
        return RestaurantEfficiency.builder()
                .orderId(firstTraceability.getOrderId())
                .orderDurationInMinutes(orderDuration.toMinutes())
                .finalStatus(finalTraceability.getNewStatus())
                .build();
    }

    private Traceability obtainFirstTraceabilityOfTheOrder(List<Traceability> traceability) {
        return traceability.stream()
                .filter(t -> t.getNewStatus().equals("PENDING"))
                .findFirst()
                .orElse(null);
    }

    private Traceability obtainFinalTraceabilityOfTheOrder(List<Traceability> traceability) {
        return traceability.stream()
                .filter(t -> t.getNewStatus().equals("DELIVERED") || t.getNewStatus().equals("CANCELLED"))
                .findFirst()
                .orElse(null);
    }

    private void validateCustomerEmail(String customerEmail, String tokenEmail) {
        if (!customerEmail.equals(tokenEmail)) {
            throw new OrderNotFromCustomerException(ExceptionConstants.ORDER_NOT_CREATED_BY_THE_CUSTOMER);
        }
    }
}
