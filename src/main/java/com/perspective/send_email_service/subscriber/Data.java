package com.perspective.send_email_service.subscriber;

public class Data {

    private String chatId;

    public Data(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "chatId='" + chatId + '\'' +
                '}';
    }
}
