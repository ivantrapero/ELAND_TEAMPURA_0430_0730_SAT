package com.trapero.cchoice.models;

public class ChatPreview {
    private String chatPartnerName;
    private String lastMessage;
    private String timestamp;

    public ChatPreview(String chatPartnerName, String lastMessage, String timestamp) {
        this.chatPartnerName = chatPartnerName;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public String getChatPartnerName() {
        return chatPartnerName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
