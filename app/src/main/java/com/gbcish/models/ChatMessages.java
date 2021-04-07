package com.gbcish.models;

import java.util.Date;

public class ChatMessages {
    private String ChatID;
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String sellerEmailID;
    private String customerID;

    public ChatMessages(String chatid,String messageText, String messageUser,String sellerEmailID,String customerID) {
        this.ChatID=chatid;
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.sellerEmailID=sellerEmailID;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessages(){

    }

    public String getChatID() {
        return ChatID;
    }

    public void setChatID(String chatID) {
        this.ChatID = chatID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
