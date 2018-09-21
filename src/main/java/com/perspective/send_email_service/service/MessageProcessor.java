package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;

public interface MessageProcessor {

    void execute(NotifyBean notifyBean);

}
