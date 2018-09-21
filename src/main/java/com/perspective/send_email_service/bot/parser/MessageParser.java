package com.perspective.send_email_service.bot.parser;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageParser {

    String parse(Message message);

}
