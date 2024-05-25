package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class BigImage implements Initializable {
    @FXML
    ImageView big_imageview;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBigImageView(Image image){
        big_imageview.setImage(image);
    }



}
