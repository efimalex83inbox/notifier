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
                        logger.error("An exception occurred when try to send the message [{}] by email",
                                notifyBean, e);
                        return bean;
                    }
                }).thenApplyAsync(bean -> {
                    try {
                        String subscribedEmail = bean.getTo();
                        Data data = subscriberStorage.get(subscribedEmail);
                        if (data != null)
                            telegramSender.send(bean, data);
                        return bean;
                    } catch (Exception e) {
                        logger.error("An exception occurred when try to send the message [{}] by telegram",
                                notifyBean, e);
                        return bean;
                    }
                });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("An exception occurred when try to execute the message [{}]", notifyBean, e);
        }

    }
}
