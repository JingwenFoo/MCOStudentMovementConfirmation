package com.example.mcostudentmovementconfirmation;

public class Model {
    //Model class
    String imageName,ImageUrl;

    public Model(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.ImageUrl = imageUrl;
    }

    //Constructors
    public Model(){ }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }
}
