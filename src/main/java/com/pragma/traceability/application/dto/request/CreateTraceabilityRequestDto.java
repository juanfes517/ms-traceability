package com.pragma.traceability.application.dto.request;

import com.pragma.traceability.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateTraceabilityRequestDto {

    @NotNull(message = DtoConstants.ORDER_ID_CANNOT_BE_BLANK)
    private Long orderId;

    @NotNull(message = DtoConstants.CUSTOMER_ID_CANNOT_BE_BLANK)
    private Long customerId;

    @Email
    @NotBlank(message = DtoConstants.CUSTOMER_EMAIL_CANNOT_BE_BLANK)
    private String customerEmail;

    private String previousStatus;

    @NotBlank(message = DtoConstants.NEW_STATUS_CANNOT_BE_BLANK)
    private String newStatus;

    private Long employeeId;

    @Email
    private String employeeEmail;
}
