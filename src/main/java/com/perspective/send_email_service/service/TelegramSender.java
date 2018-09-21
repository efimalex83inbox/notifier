package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.bot.EmailSubscriberBot;
import com.perspective.send_email_service.subscriber.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramSender implements NotifyService {

    private Logger logger = LoggerFactory.getLogger(TelegramSender.class);

    private final EmailSubscriberBot bot;

    public TelegramSender(EmailSubscriberBot bot) {
        this.bot = bot;
    }

    @Override
    public void send(NotifyBean notifyBean, Data data) {
        bot.sendMsg(data.getChatId(), notifyBean.getText());
        logger.info("Send by telegram: " + notifyBean);

    }
}
