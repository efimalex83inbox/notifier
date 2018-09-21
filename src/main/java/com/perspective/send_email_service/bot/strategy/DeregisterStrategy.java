package com.perspective.send_email_service.bot.strategy;

import com.perspective.send_email_service.subscriber.Data;
import com.perspective.send_email_service.subscriber.TelegramSubscriberStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component("/deregister")
public class DeregisterStrategy implements BotCommandExecuteStrategy {

    private final TelegramSubscriberStorage<Data> storage;

    public DeregisterStrategy(TelegramSubscriberStorage<Data> storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String cmdValue, Message message) {
        this.storage.remove(cmdValue);
    }
}
