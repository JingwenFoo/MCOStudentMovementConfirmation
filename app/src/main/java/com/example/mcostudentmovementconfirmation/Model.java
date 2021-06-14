package com.example.mcostudentmovementconfirmation;

public class Model {
    String postID, time, description, postImage, publisher;

    public Model() {
    }

    public Model(String postID, String time, String description, String postImage, String publisher) {
        this.postID = postID;
        this.time = time;
        this.description = description;
        this.postImage = postImage;
        this.publisher = publisher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
