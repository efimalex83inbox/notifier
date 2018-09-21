package com.perspective.send_email_service.bot.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

@Component
public class BotCmdValueParser implements MessageParser {

    @Override
    public String parse(Message message) {
        String text = message.getText();
        if (!message.isCommand())
            return StringUtils.EMPTY;

        List<MessageEntity> entities = message.getEntities();
        MessageEntity command = entities.get(0);
        return text.substring(command.getLength()).trim();
    }
}
