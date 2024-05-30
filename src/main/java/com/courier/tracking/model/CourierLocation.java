package com.courier.tracking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "locations")
@Data
public class CourierLocation {
    @Id
    private String id;
    private String courierId;
    private LocalDateTime timestamp;
    private double latitude;
    private double longitude;
    private Store enteredStoreInfo;
}
