package com.perspective.send_email_service.bot.formatter;

import com.perspective.send_email_service.bean.NotifyBean;
import org.springframework.stereotype.Component;

@Component
public class MDMessageFormatter implements MessageFormatter {

    private final static String TEMPLATE = "```To:``` %s  ```Subject:``` %s  ```Body:``` %s";

    @Override
    public String format(NotifyBean bean) {
        return String.format(TEMPLATE, bean.getTo(), bean.getSubject(), bean.getText());
    }

}
