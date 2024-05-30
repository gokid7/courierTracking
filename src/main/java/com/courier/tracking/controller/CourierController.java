package com.courier.tracking.controller;

import com.courier.tracking.aspect.ExecutionTime;
import com.courier.tracking.model.Courier;
import com.courier.tracking.model.CourierLocation;
import com.courier.tracking.model.response.CourierBaseResponse;
import com.courier.tracking.service.CourierServiceImpl;
import com.courier.tracking.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {
    @Autowired
    private CourierServiceImpl courierServiceImpl;

    @ExecutionTime
    @PostMapping(value = "/location" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourierBaseResponse<String>> logLocation(@RequestBody CourierLocation courierLocation) {
       String logLocationString = courierServiceImpl.logLocation(courierLocation);
       return ResponseEntity.ok(new CourierBaseResponse<>(ResponseUtil.createSuccessResponse(),logLocationString,true));
    }

    @ExecutionTime
    @GetMapping(value = "/distance/{courierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourierBaseResponse<Double>> getTotalTravelDistance(@PathVariable String courierId) {
        Double totalDistance = courierServiceImpl.getTotalTravelDistance(courierId);
        return ResponseEntity.ok(new CourierBaseResponse<>(ResponseUtil.createSuccessResponse(),totalDistance,true));
    }

    @ExecutionTime
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourierBaseResponse<Courier>> createCourier(@RequestBody Courier courier) {
        Courier courierInfo = courierServiceImpl.createCourier(courier);
        return ResponseEntity.ok(new CourierBaseResponse<>(ResponseUtil.createSuccessResponse(),courier,true));
    }

    @ExecutionTime
    @PutMapping("/{id}")
    public ResponseEntity<CourierBaseResponse<Courier>> updateCourier(@PathVariable String id, @RequestBody Courier courier) {
        Courier updatedCourierInfo = courierServiceImpl.updateCourier(id, courier);
        return ResponseEntity.ok(new CourierBaseResponse<>(ResponseUtil.createSuccessResponse(),updatedCourierInfo,true));
    }
}
