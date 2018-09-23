package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.subscriber.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements NotifyService {

    private Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender emailSender;

    public EmailSender(@Qualifier("getJavaMailSender") JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(NotifyBean bean, Data data) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(bean.getTo());
        message.setSubject(bean.getSubject());
        message.setText(bean.getText());
        emailSender.send(message);
    }
}

