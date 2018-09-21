package com.perspective.send_email_service.bot.strategy;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DefaultStrategy implements BotCommandExecuteStrategy {

    @Override
    public void execute(String cmdValue, Message message) {
        //write into log
    }
}
