package com.perspective.send_email_service.dataaccess;

import com.perspective.send_email_service.configuration.property.EmailDestionationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class SimpleEmailRecipientDao implements EmailRecipientDao {

    private final EmailDestionationProperties emailDestionationProperties;

    public SimpleEmailRecipientDao(EmailDestionationProperties emailDestionationProperties) {
        this.emailDestionationProperties = emailDestionationProperties;
    }

    @Override
    public List<String> findAll() {
        String raw = emailDestionationProperties.getDestination();
        String[] emails = StringUtils.split(raw, "|");
        return Arrays.asList(emails);
    }
}
