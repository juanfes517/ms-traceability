package com.pragma.traceability.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantEfficiency {

    private Long orderId;
    private double orderDurationInMinutes;
    private String finalStatus;
}
