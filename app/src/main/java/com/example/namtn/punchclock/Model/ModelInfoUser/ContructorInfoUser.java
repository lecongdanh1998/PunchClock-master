package com.example.namtn.punchclock.Model.ModelInfoUser;

public class ContructorInfoUser {
    String id;
    String username;
    String imageURL;
    String online;
    String birthday;
    String gender;
    String imageBackground;
    public ContructorInfoUser(String id, String username, String imageURL, String online, String birthday, String gender,String imageBackground) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.online = online;
        this.birthday = birthday;
        this.gender = gender;
        this.imageBackground = imageBackground;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public ContructorInfoUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
