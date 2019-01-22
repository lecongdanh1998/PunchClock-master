package com.example.namtn.punchclock.Model.ModelChat;

public class ContructorChatUser2 {
    String username;
    String imageURL;
    private String fromId;
    private String message;
    private String time;
    private String toId;
    private String type;
    private String status;
    private String online;
    public ContructorChatUser2(String username, String imageURL, String fromId, String message, String time, String toId,String type, String status,String online) {
        this.username = username;
        this.imageURL = imageURL;
        this.fromId = fromId;
        this.message = message;
        this.time = time;
        this.toId = toId;
        this.type = type;
        this.status = status;
        this.online = online;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ContructorChatUser2() {
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
