package com.courier.tracking.observer;

import com.courier.tracking.model.Courier;
import com.courier.tracking.model.Store;

public interface StoreEntryListener {
    void onStoreEntry(Courier courier, Store store);
}
