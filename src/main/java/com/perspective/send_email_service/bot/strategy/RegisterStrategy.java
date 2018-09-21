package com.perspective.send_email_service.bot.strategy;

import com.perspective.send_email_service.subscriber.Data;
import com.perspective.send_email_service.subscriber.TelegramSubscriberStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component("/register")
public class RegisterStrategy implements BotCommandExecuteStrategy {

    private final TelegramSubscriberStorage<Data> storage;

    public RegisterStrategy(TelegramSubscriberStorage<Data> storage) {
        this.storage = storage;
    }

    @Override
    public void execute(String cmdValue, Message message) {
        this.storage.put(cmdValue, new Data(message.getChatId().toString()));
    }
}
