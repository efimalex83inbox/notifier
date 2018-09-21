package com.perspective.send_email_service.api;

import com.perspective.send_email_service.bean.NotifyBean;
import com.perspective.send_email_service.dataaccess.EmailRecipientDao;
import com.perspective.send_email_service.service.MessageProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> send(@RequestBody NotifyBean notifyBean) {


        notifyService.execute(notifyBean);

//        IPayloadWrapper formDefinition;
//        try {
//            IPayloadWrapper message = this.payloadFactory.create();
//            this.providerField.out(message, provider);
//            formDefinition = this.obtainFormDefinitionService.execute(message);
//        } catch (Exception e) {
//            logger.error("An error occurred when call [getFormDefinition].", e);
//            throw new IllegalStateException(String.format("An exception occurred when try to " +
//                    "obtain the form definition for provider [%s]", provider), e);
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notifier/send", method = RequestMethod.GET)
    public ResponseEntity<?> sendTest() {

        List<String> all = recipientDao.findAll();

        all.forEach(email -> {
            NotifyBean notifyBean = new NotifyBean();
            notifyBean.setTo(email);
            notifyBean.setSubject("12345");
            notifyBean.setText("text text text");
            notifyService.execute(notifyBean);
        });



//        IPayloadWrapper formDefinition;
//        try {
//            IPayloadWrapper message = this.payloadFactory.create();
//            this.providerField.out(message, provider);
//            formDefinition = this.obtainFormDefinitionService.execute(message);
//        } catch (Exception e) {
//            logger.error("An error occurred when call [getFormDefinition].", e);
//            throw new IllegalStateException(String.format("An exception occurred when try to " +
//                    "obtain the form definition for provider [%s]", provider), e);
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
