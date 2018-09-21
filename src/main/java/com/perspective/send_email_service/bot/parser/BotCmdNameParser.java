package com.perspective.send_email_service.bot.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

@Component
public class BotCmdNameParser implements MessageParser {

    @Override
    public String parse(Message message) {
        if (!message.isCommand())
            return StringUtils.EMPTY;

        List<MessageEntity> entities = message.getEntities();
        MessageEntity command = entities.get(0);
        return command.getText();
    }
}
