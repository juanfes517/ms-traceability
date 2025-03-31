package com.pragma.traceability.infrastructure.out.mongo.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection  = "traceability")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TraceabilityCollection {

    @Transient
    public static final String SEQUENCE_NAME = "traceability_sequence";

    @Id
    private Long id;

    @Field(name = "order_id")
    private Long orderId;

    @Field(name = "customer_id")
    private Long customerId;

    @Field(name = "customer_email")
    private String customerEmail;

    @Field(name = "date")
    private LocalDateTime date;

    @Field(name = "previous_status")
    private String previousStatus;

    @Field(name = "new_status")
    private String newStatus;

    @Field(name = "employee_id")
    private Long employeeId;

    @Field(name = "employee_email")
    private String employeeEmail;
}
