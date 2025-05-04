package com.trapero.cchoice.models;

public class Message {

    private String senderName;
    private String lastMessage;
    private String timestamp;
    private int profileImageResId;

    public Message(String senderName, String lastMessage, String timestamp, int profileImageResId) {
        this.senderName = senderName;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.profileImageResId = profileImageResId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}
