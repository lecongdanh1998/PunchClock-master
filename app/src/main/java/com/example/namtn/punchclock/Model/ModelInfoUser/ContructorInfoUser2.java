package com.example.namtn.punchclock.Model.ModelInfoUser;

public class ContructorInfoUser2 {
    String id;
    String username;
    String imageURL;

    public ContructorInfoUser2(String id, String imageURL, String username) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



    public ContructorInfoUser2() {
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
