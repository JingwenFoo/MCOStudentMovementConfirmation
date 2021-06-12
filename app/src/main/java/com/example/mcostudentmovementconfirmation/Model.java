package com.example.mcostudentmovementconfirmation;

public class Model {
    //Model class
    String imageName,imageUrl;

    //Constructors
    public Model(){ }

    public Model(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    //getter
    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    //setter
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
