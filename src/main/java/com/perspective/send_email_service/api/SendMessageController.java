package com.perspective.send_email_service.api;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.dataaccess.EmailRecipientDao;
import com.perspective.send_email_service.service.MessageProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SendMessageController {

    private final MessageProcessor notifyService;
    private final EmailRecipientDao recipientDao;

    public SendMessageController(MessageProcessor notifyService, EmailRecipientDao recipientDao) {
        this.notifyService = notifyService;
        this.recipientDao = recipientDao;
    }

    @RequestMapping(value = "/api/notifier/send", method = RequestMethod.POST)
    public ResponseEntity<?> send(@Valid @RequestBody NotifyBean notifyBean) {
        List<String> all = recipientDao.findAll();
        all.forEach(email -> {
            NotifyBean nb = new NotifyBean();
            nb.setTo(email);
            nb.setSubject(notifyBean.getSubject());
            nb.setText(notifyBean.getText());
            notifyService.execute(nb);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
