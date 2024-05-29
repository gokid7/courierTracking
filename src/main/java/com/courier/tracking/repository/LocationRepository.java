package com.courier.tracking.repository;

import com.courier.tracking.model.CourierLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends MongoRepository<CourierLocation, String> {
    List<CourierLocation> findByCourierId(String courierId);
}
