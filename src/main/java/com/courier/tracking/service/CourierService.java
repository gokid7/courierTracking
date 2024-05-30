package com.courier.tracking.service;

import com.courier.tracking.model.Courier;
import com.courier.tracking.model.CourierLocation;

public interface CourierService {
    String logLocation(CourierLocation courierLocation);
    Double getTotalTravelDistance(String courierId);
    Courier createCourier(Courier courier);
    Courier updateCourier(String id, Courier courierDetails);
}
