package com.courier.tracking.service;

import com.courier.tracking.constant.Constants;
import com.courier.tracking.exception.ResourceNotFoundException;
import com.courier.tracking.model.Courier;
import com.courier.tracking.model.CourierLocation;
import com.courier.tracking.model.Store;
import com.courier.tracking.observer.StoreEntryListener;
import com.courier.tracking.observer.StoreObserver;
import com.courier.tracking.repository.CourierRepository;
import com.courier.tracking.repository.LocationRepository;
import com.courier.tracking.util.StoreLoaderUtil;
import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService{

    private final CourierRepository courierRepository;
    private final LocationRepository locationRepository;

    private StoreObserver storeObserver = new StoreObserver();

    public CourierServiceImpl(CourierRepository courierRepository, LocationRepository locationRepository) {
        this.courierRepository = courierRepository;
        this.locationRepository = locationRepository;
        List<Store> stores = StoreLoaderUtil.getInstance().getStores();
        storeObserver.addListener(new StoreEntryListener() {
            @Override
            public void onStoreEntry(Courier courier, Store store) {
                System.out.println("A courier named " + courier.getName() + " entered the " + store.getName() + " store.");
            }
        });
    }

    @Override
    public String logLocation(CourierLocation courierLocation) {
        courierLocation.setTimestamp(LocalDateTime.now());
        locationRepository.save(courierLocation);
        String logLocationInfo = checkStoreEntry(courierLocation);
        updateTotalDistance(courierLocation.getCourierId());
        return logLocationInfo;
    }

    @Override
    public Double getTotalTravelDistance(String courierId) {
        Optional<Courier> courier = courierRepository.findById(courierId);
        return courier.map(Courier::getTotalDistance).orElse(0.0);
    }

    @Override
    public Courier createCourier(Courier courier) {
        courier.setTotalDistance(0.0);
        return courierRepository.save(courier);
    }

    @Override
    public Courier updateCourier(String id, Courier courierDetails) {
        Optional<Courier> optionalCourier = courierRepository.findById(id);
        if (optionalCourier.isPresent()) {
            Courier courier = optionalCourier.get();
            courier.setName(courierDetails.getName());
            return courierRepository.save(courier);
        } else {
            throw new ResourceNotFoundException(Constants.ExceptionConstants.COURIER_NOT_FOUND + id);
        }
    }

    private String checkStoreEntry(CourierLocation courierLocation) {
        List<Store> stores = StoreLoaderUtil.getInstance().getStores();
        Courier courier = courierRepository.findById(courierLocation.getCourierId()).orElseThrow(() -> new ResourceNotFoundException("Courier not found with id " + courierLocation.getCourierId()));

        return stores.stream()
                .filter(store -> SloppyMath.haversinMeters(courierLocation.getLatitude(), courierLocation.getLongitude(), store.getLat(), store.getLng()) <= 100)
                .filter(store -> !hasRecentEntry(courierLocation, store))
                .findFirst()
                .map(store -> {
                    courierLocation.setEnteredStoreInfo(store);
                    locationRepository.save(courierLocation);
                    storeObserver.notifyStoreEntry(courier, store);
                    return courier.getName() + Constants.GlobalConstants.COURIER_NAME_INFO + store.getName() + Constants.GlobalConstants.STORE_ENTER_INFO;
                })
                .orElse(Constants.GlobalConstants.NO_ENTRY_INFO);
    }

    private boolean hasRecentEntry(CourierLocation courierLocation, Store store) {
        List<CourierLocation> recentCourierLocations = locationRepository.findByCourierId(courierLocation.getCourierId());
        return recentCourierLocations.stream()
                .filter(loc -> loc.getEnteredStoreInfo() != null && loc.getEnteredStoreInfo().getName().equals(store.getName()))
                .anyMatch(loc -> loc.getTimestamp().plusMinutes(1).isAfter(LocalDateTime.now()));
    }

    private void updateTotalDistance(String courierId) {
        List<CourierLocation> courierLocations = locationRepository.findByCourierId(courierId);
        double totalDistance = 0.0;

        for (int i = 1; i < courierLocations.size(); i++) {
            CourierLocation loc1 = courierLocations.get(i - 1);
            CourierLocation loc2 = courierLocations.get(i);
            totalDistance += SloppyMath.haversinMeters(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude());
        }

        Optional<Courier> optionalCourier = courierRepository.findById(courierId);
        if (optionalCourier.isPresent()) {
            Courier courier = optionalCourier.get();
            courier.setTotalDistance(totalDistance);
            courierRepository.save(courier);
        }
    }

}
