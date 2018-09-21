package com.perspective.send_email_service.service;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.subscriber.Data;

public interface NotifyService {

    void send(NotifyBean notifyBean, Data data);

}
