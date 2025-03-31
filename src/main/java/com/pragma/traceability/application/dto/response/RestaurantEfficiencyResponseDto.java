package com.pragma.traceability.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantEfficiencyResponseDto {

    private Long orderId;
    private double orderDurationInMinutes;
    private String finalStatus;
}
