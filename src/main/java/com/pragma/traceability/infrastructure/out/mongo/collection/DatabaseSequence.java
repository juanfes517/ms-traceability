package com.pragma.traceability.infrastructure.out.mongo.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;
}
