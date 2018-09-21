package com.perspective.send_email_service.dataaccess;

import java.util.List;

public interface EmailRecipientDao {

    List<String> findAll();

}
