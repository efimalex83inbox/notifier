package com.perspective.send_email_service.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NotifyBean {

    private String to;

    @NotNull(message = "{bean.subject.not.empty}")
    @NotEmpty
    private String subject;

    @NotNull(message = "{bean.subject.not.empty}")
    @NotEmpty
    private String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NotifyBean{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
