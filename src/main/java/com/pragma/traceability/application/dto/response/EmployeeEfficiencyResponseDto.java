package com.pragma.traceability.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmployeeEfficiencyResponseDto {

    private Long employeeId;
    private String employeeEmail;
    private double averageProcessingTimeInSeconds;
}
