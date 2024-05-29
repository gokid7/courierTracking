package com.courier.tracking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "couriers")
@Data
public class Courier {
    @Id
    private String id;
    private String name;
    private Double totalDistance;
}
