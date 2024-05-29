package com.courier.tracking.service;

import com.courier.tracking.exception.ResourceNotFoundException;
import com.courier.tracking.model.Courier;
import com.courier.tracking.model.CourierLocation;
import com.courier.tracking.model.Store;
import com.courier.tracking.observer.StoreObserver;
import com.courier.tracking.repository.CourierRepository;
import com.courier.tracking.repository.LocationRepository;
import com.courier.tracking.util.StoreLoaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;
    @Mock
    private CourierRepository courierRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private StoreLoaderUtil storeLoaderUtil;
    @Mock
    private StoreObserver storeObserver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourier() {
        Courier courier = new Courier();
        courier.setName("Test Courier");

        when(courierRepository.save(any(Courier.class))).thenReturn(courier);

        Courier createdCourier = courierService.createCourier(courier);

        assertNotNull(createdCourier);
        assertEquals("Test Courier", createdCourier.getName());
        assertEquals(0.0, createdCourier.getTotalDistance());
    }

    @Test
    void testUpdateCourier() {
        Courier existingCourier = new Courier();
        existingCourier.setId("123");
        existingCourier.setName("Test Courier Old Name");

        Courier updatedDetails = new Courier();
        updatedDetails.setName("Test Courier New Name");

        when(courierRepository.findById("123")).thenReturn(Optional.of(existingCourier));
        when(courierRepository.save(any(Courier.class))).thenReturn(existingCourier);

        Courier updatedCourier = courierService.updateCourier("123", updatedDetails);

        assertNotNull(updatedCourier);
        assertEquals("Test Courier New Name", updatedCourier.getName());
    }

    @Test
    void testUpdateCourierNotFound() {
        Courier updatedDetails = new Courier();
        updatedDetails.setName("Test Courier New Name");

        when(courierRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            courierService.updateCourier("123", updatedDetails);
        });
    }

    @Test
    void testLogLocation() {
        CourierLocation location = new CourierLocation();
        location.setCourierId("123");
        location.setLatitude(41.015137);
        location.setLongitude(28.979530);
        location.setTimestamp(LocalDateTime.now());

        when(locationRepository.save(any(CourierLocation.class))).thenReturn(location);
        when(courierRepository.findById("123")).thenReturn(Optional.of(new Courier()));

        Store store = new Store();
        store.setName("Migros Store");
        store.setLat(41.015137);
        store.setLng(28.979530);
        List<Store> stores = Collections.singletonList(store);

        when(storeLoaderUtil.getStores()).thenReturn(stores);

        courierService.logLocation(location);

        verify(locationRepository, times(1)).save(location);
        //verify(storeObserver, times(1)).notifyStoreEntry(any(Courier.class), any(Store.class));
    }

    @Test
    void testGetTotalTravelDistance() {
        Courier courier = new Courier();
        courier.setId("123");
        courier.setTotalDistance(100.0);

        when(courierRepository.findById("123")).thenReturn(Optional.of(courier));

        Double distance = courierService.getTotalTravelDistance("123");

        assertNotNull(distance);
        assertEquals(100.0, distance);
    }

    @Test
    void testGetTotalTravelDistanceCourierNotFound() {
        when(courierRepository.findById("123")).thenReturn(Optional.empty());

        Double distance = courierService.getTotalTravelDistance("123");

        assertNotNull(distance);
        assertEquals(0.0, distance);
    }
}
