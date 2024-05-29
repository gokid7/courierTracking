package com.courier.tracking.service;

import com.courier.tracking.constant.Constants;
import com.courier.tracking.exception.ResourceNotFoundException;
import com.courier.tracking.model.Courier;
import com.courier.tracking.model.CourierLocation;
import com.courier.tracking.model.Store;
import com.courier.tracking.model.response.CourierBaseResponse;
import com.courier.tracking.observer.StoreEntryListener;
import com.courier.tracking.observer.StoreObserver;
import com.courier.tracking.repository.CourierRepository;
import com.courier.tracking.repository.LocationRepository;
import com.courier.tracking.util.StoreLoaderUtil;
import org.apache.lucene.util.SloppyMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private LocationRepository locationRepository;

    private StoreObserver storeObserver = new StoreObserver();

    public CourierService() {
        List<Store> stores = StoreLoaderUtil.getInstance().getStores();
        storeObserver.addListener(new StoreEntryListener() {
            @Override
            public void onStoreEntry(Courier courier, Store store) {
                System.out.println("A courier named " + courier.getName() + " entered the " + store.getName() + " store.");
            }
        });
    }

    public String logLocation(CourierLocation courierLocation) {
        locationRepository.save(courierLocation);
        String logLocationInfo = checkStoreEntry(courierLocation);
        updateTotalDistance(courierLocation.getCourierId());
        return logLocationInfo;
    }

    public Double getTotalTravelDistance(String courierId) {
        Optional<Courier> courier = courierRepository.findById(courierId);
        return courier.map(Courier::getTotalDistance).orElse(0.0);
    }

    public Courier createCourier(Courier courier) {
        courier.setTotalDistance(0.0);
        return courierRepository.save(courier);
    }

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

        for (Store store : stores) {
            double distance = SloppyMath.haversinMeters(courierLocation.getLatitude(), courierLocation.getLongitude(), store.getLat(), store.getLng());
            if (distance <= 100) {
                List<CourierLocation> recentCourierLocations = locationRepository.findByCourierId(courierLocation.getCourierId());
                boolean hasRecentEntry = recentCourierLocations.stream()
                        .anyMatch(loc -> loc.getTimestamp().isAfter(LocalDateTime.now().minusMinutes(1)) &&
                                SloppyMath.haversinMeters(loc.getLatitude(), loc.getLongitude(), store.getLat(), store.getLng()) <= 100);

                if (!hasRecentEntry) {
                    storeObserver.notifyStoreEntry(courier, store);
                    return courier.getName() + Constants.GlobalConstants.COURIER_NAME_INFO
                            + store.getName() + Constants.GlobalConstants.STORE_ENTER_INFO;
                }
            }
        }
        return Constants.GlobalConstants.NO_ENTRY_INFO;
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
