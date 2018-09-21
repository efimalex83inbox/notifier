package com.perspective.send_email_service.bot.strategy;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotCommandExecuteStrategy {

    void execute(String cmdValue, Message message);
}
