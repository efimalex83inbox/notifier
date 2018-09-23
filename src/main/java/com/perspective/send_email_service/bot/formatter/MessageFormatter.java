package com.perspective.send_email_service.bot.formatter;

import com.perspective.send_email_service.bean.NotifyBean;

public interface MessageFormatter {

    String format(NotifyBean bean);

}
