package com.example.namtn.punchclock.Model.ModelChat;

public class ContructorChat1 {
    private String sender;
    private String receiver;
    private String type;
    private String message;
    private String time;
    String username;
    String imageURL;
    String imageUrlUid;
    String status;
    String status1;
    public ContructorChat1(String sender, String receiver, String type, String message, String time, String username, String imageURL,String imageUrlUid,String status,String status1) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.message = message;
        this.time = time;
        this.username = username;
        this.imageURL = imageURL;
        this.imageUrlUid = imageUrlUid;
        this.status = status;
        this.status1 = status1;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrlUid() {
        return imageUrlUid;
    }

    public void setImageUrlUid(String imageUrlUid) {
        this.imageUrlUid = imageUrlUid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ContructorChat1() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
