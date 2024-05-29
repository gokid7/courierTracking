package com.courier.tracking.observer;

import com.courier.tracking.model.Courier;
import com.courier.tracking.model.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreObserver {
    private List<StoreEntryListener> listeners = new ArrayList<>();

    public void addListener(StoreEntryListener listener) {
        listeners.add(listener);
    }

    public void notifyStoreEntry(Courier courier, Store store) {
        for (StoreEntryListener listener : listeners) {
            listener.onStoreEntry(courier, store);
        }
    }
}
