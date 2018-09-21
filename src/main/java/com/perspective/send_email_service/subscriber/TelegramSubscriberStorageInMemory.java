package com.perspective.send_email_service.subscriber;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TelegramSubscriberStorageInMemory implements TelegramSubscriberStorage<Data> {

    private final ConcurrentMap<String, Data> storage;

    public TelegramSubscriberStorageInMemory() {
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public Data get(String key) {
        return storage.get(key);
    }

    @Override
    public void put(String key, Data value) {
        storage.putIfAbsent(key, value);
        // TODO: 20.09.2018 for test only
        System.out.println(storage.toString());
    }

    @Override
    public void remove(String key) {
        this.storage.remove(key);

        // TODO: 20.09.2018 for test only
        System.out.println(storage.toString());
    }

    @Override
    public boolean isExist(String key) {
        return this.storage.get(key) != null;
    }
}
