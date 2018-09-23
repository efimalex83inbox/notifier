package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.bot.EmailSubscriberBot;
import com.perspective.send_email_service.bot.formatter.MessageFormatter;
import com.perspective.send_email_service.subscriber.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramSender implements NotifyService {

    private Logger logger = LoggerFactory.getLogger(TelegramSender.class);

    private final EmailSubscriberBot bot;
    private final MessageFormatter messageFormatter;

    public TelegramSender(EmailSubscriberBot bot, MessageFormatter messageFormatter) {
        this.bot = bot;
        this.messageFormatter = messageFormatter;
    }

    @Override
    public void send(NotifyBean notifyBean, Data data) {
        bot.sendMsg(data.getChatId(), this.messageFormatter.format(notifyBean));
        logger.info("The message was sent into telegram chat with ID {}.", data.getChatId());
    }
}
