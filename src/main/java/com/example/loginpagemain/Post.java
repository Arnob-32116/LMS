package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Post implements Initializable{
    @FXML
    Label username_lable,tag_lable,date_lable;
    @FXML
    Text post_text_text;
    @FXML
    ImageView post_image_view;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginDatabase loginDatabase = new LoginDatabase();
        username_lable.setText(MainPage.status_username);
        post_text_text.setText(MainPage.status);
        tag_lable.setText(MainPage.post_tag);
        date_lable.setText(MainPage.post_date);
    if(!MainPage.image_url.equals("none")) {
            try {
                Image image ;
                assert MainPage.image_url != null;
                image = new Image(new FileInputStream(MainPage.image_url));
                post_image_view.setImage(image);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void setImageView(){
        Image image = new Image("icon.png");
        post_image_view.setImage(image);
    }

}
