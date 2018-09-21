package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.subscriber.Data;
import com.perspective.send_email_service.subscriber.TelegramSubscriberStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class AsyncMessageProcessor implements MessageProcessor {

    private Logger logger = LoggerFactory.getLogger(AsyncMessageProcessor.class);

    private final NotifyService emailSender;
    private final NotifyService telegramSender;

    private final TelegramSubscriberStorage<Data> subscriberStorage;

    public AsyncMessageProcessor(@Qualifier("emailSender") NotifyService emailSender,
                                 @Qualifier("telegramSender") NotifyService telegramSender,
                                 TelegramSubscriberStorage<Data> subscriberStorage) {

        this.emailSender = emailSender;
        this.telegramSender = telegramSender;
        this.subscriberStorage = subscriberStorage;
    }

    @Override
    public void execute(NotifyBean notifyBean) {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> notifyBean)
                .thenApplyAsync(bean -> {
                    try {
                        emailSender.send(bean, new Data(""));
                        return bean;
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage(), e);
                        //todo logging
                    }
                }).thenApplyAsync(bean -> {
                    try {
                        String subscribedEmail = bean.getTo();
                        Data data = subscriberStorage.get(subscribedEmail);
                        if (data != null)
                            telegramSender.send(bean, data);
                        return bean;
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage(), e);
                        //todo logging
                    }
                });

    }
}
