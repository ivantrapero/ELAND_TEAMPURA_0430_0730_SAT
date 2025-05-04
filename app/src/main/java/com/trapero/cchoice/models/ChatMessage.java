package com.trapero.cchoice.models;

public class ChatMessage {
    private String sender;
    private String text;
    private String timestamp;
    private int icon;
    private boolean isUser;

    public ChatMessage(String sender, String text, String timestamp, int icon, boolean isUser) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
        this.icon = icon;
        this.isUser = isUser;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isUser() {
        return isUser;
    }
}
