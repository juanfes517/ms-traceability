package com.pragma.traceability.application.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateTraceabilityRequestDto {

    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;
}
