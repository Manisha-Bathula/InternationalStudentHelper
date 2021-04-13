package com.gbcish.models;

public class ReceiveChatMessages {

    private String ToUuid;
    private Messages messages;

    public ReceiveChatMessages(String toUuid, Messages messages) {
        ToUuid = toUuid;
        this.messages = messages;
    }

    public String getToUuid() {
        return ToUuid;
    }

    public void setToUuid(String toUuid) {
        ToUuid = toUuid;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
