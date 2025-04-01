package com.pragma.traceability.domain.usecase;

import com.pragma.traceability.domain.api.ITraceabilityServicePort;
import com.pragma.traceability.domain.exception.OrderNotFromCustomerException;
import com.pragma.traceability.domain.helper.constants.ExceptionConstants;
import com.pragma.traceability.domain.model.EmployeeEfficiency;
import com.pragma.traceability.domain.model.RestaurantEfficiency;
import com.pragma.traceability.domain.model.Traceability;
import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<EmployeeEfficiency> getEmployeeEfficiency(List<Long> employeeIds) {
        List<EmployeeEfficiency> employeeEfficiencies = new ArrayList<>();

        for (Long employeeId : employeeIds) {
            List<Traceability> traceabilityPreparing = traceabilityPersistencePort.
                    findAllByEmployeeIdAndNewStatus(employeeId, "PREPARING");
            List<Traceability> traceabilityDelivered = traceabilityPersistencePort.
                    findAllByEmployeeIdAndNewStatus(employeeId, "DELIVERED");

            EmployeeEfficiency employeeEfficiency = this.createEmployeeEfficiency(traceabilityPreparing, traceabilityDelivered);

            if (employeeEfficiency != null) {
                employeeEfficiencies.add(employeeEfficiency);
            }
        }

        return employeeEfficiencies.stream()
                .sorted(Comparator.comparingDouble(EmployeeEfficiency::getAverageProcessingTimeInSeconds))
                .toList();
    }

    private EmployeeEfficiency createEmployeeEfficiency(List<Traceability> traceabilityPreparing, List<Traceability> traceabilityDelivered) {

        if (traceabilityDelivered.isEmpty()) {
            return null;
        }

        double preparationTimes = 0.0;

        for (Traceability tDelivered : traceabilityDelivered) {
            Long orderId = tDelivered.getOrderId();

            Traceability tPreparing = traceabilityPreparing.stream()
                    .filter(t -> Objects.equals(t.getOrderId(), orderId))
                    .findFirst()
                    .orElseThrow();

            LocalDateTime deliveredDate = tDelivered.getDate();
            LocalDateTime preparationDate = tPreparing.getDate();

            Duration orderDuration = Duration.between(preparationDate, deliveredDate);
            preparationTimes += orderDuration.toSeconds();
        }

        return EmployeeEfficiency.builder()
                .employeeId(traceabilityDelivered.get(0).getEmployeeId())
                .employeeEmail(traceabilityDelivered.get(0).getEmployeeEmail())
                .averageProcessingTimeInSeconds(preparationTimes/traceabilityDelivered.size())
                .build();
    }

    private RestaurantEfficiency createRestaurantEfficiency(List<Traceability> traceability) {

        Traceability firstTraceability = this.obtainTraceabilityOfTheOrder(traceability);
        Traceability finalTraceability = this.obtainFinalTraceabilityOfTheOrder(traceability);

        if (firstTraceability == null || finalTraceability == null) {
            return null;
        }

        Duration orderDuration = Duration.between(firstTraceability.getDate(), finalTraceability.getDate());
        return RestaurantEfficiency.builder()
                .orderId(firstTraceability.getOrderId())
                .orderDurationInSeconds(orderDuration.toSeconds())
                .finalStatus(finalTraceability.getNewStatus())
                .build();
    }

    private Traceability obtainTraceabilityOfTheOrder(List<Traceability> traceability) {
        return traceability.stream()
                .filter(t -> t.getNewStatus().equals("PENDING"))
                .findFirst()
                .orElse(null);
    }

    private Traceability obtainFinalTraceabilityOfTheOrder(List<Traceability> traceability) {
        return traceability.stream()
                .filter(t -> t.getNewStatus().equals("DELIVERED") || t.getNewStatus().equals("CANCELED"))
                .findFirst()
                .orElse(null);
    }

    private void validateCustomerEmail(String customerEmail, String tokenEmail) {
        if (!customerEmail.equals(tokenEmail)) {
            throw new OrderNotFromCustomerException(ExceptionConstants.ORDER_NOT_CREATED_BY_THE_CUSTOMER);
        }
    }
}
