package com.gbcish.models;

import java.io.Serializable;

public class ReceiveChatMessages implements Serializable {

    private String ToUuid;
    private String FromUuid;
    private Messages messages;

    public ReceiveChatMessages(String toUuid,String FromUuis, Messages messages) {
        ToUuid = toUuid;
        this.messages = messages;
        this.FromUuid=FromUuis;
    }

    public String getFromUuid() {
        return FromUuid;
    }

    public void setFromUuid(String fromUuid) {
        FromUuid = fromUuid;
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
