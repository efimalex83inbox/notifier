package com.perspective.send_email_service.subscriber;

public interface TelegramSubscriberStorage<T> {

    T get(String key);

    void put(String key, T value);

    void remove(String key);

    boolean isExist(String key);

}
