package com.example.namtn.punchclock.ModelView;

import java.io.Serializable;

public class Conversation implements Serializable {

    private String author;
    private String caption;
    private String image;
    private String create_at;

    public Conversation(String author, String caption, String image, String create_at) {
        this.author = author;
        this.caption = caption;
        this.image = image;
        this.create_at = create_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
}
