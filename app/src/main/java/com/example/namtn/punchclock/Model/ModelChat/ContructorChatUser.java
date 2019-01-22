package com.example.namtn.punchclock.Model.ModelChat;

public class ContructorChatUser {
    private String fromId;
    private String message;
    private String time;
    private String toId;
    private String type;
    private String status;
    public ContructorChatUser(String fromId, String message, String time, String toId, String type,String status) {
        this.fromId = fromId;
        this.message = message;
        this.time = time;
        this.toId = toId;
        this.type = type;
        this.status = status;
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

    public ContructorChatUser() {
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
