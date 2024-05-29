package com.courier.tracking.util;

import com.courier.tracking.model.Store;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.IOException;

import java.util.List;

public class StoreLoaderUtil {

    private static StoreLoaderUtil instance;
    private List<Store> stores;

    private StoreLoaderUtil(){
        this.stores = loadStores();
    }

    public static StoreLoaderUtil getInstance() {
        if (instance == null) {
            instance = new StoreLoaderUtil();
        }
        return instance;
    }

    public List<Store> getStores() {
        return stores;
    }
    public static List<Store> loadStores() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = StoreLoaderUtil.class.getResourceAsStream("/stores.json")) {
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load stores.json", e);
        }
    }
}

