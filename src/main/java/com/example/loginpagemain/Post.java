package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                if(!MainPage.image_url.isEmpty()) {
                    image = new Image(new FileInputStream(MainPage.image_url));
                    post_image_view.setImage(image);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error In the Post initializer");
            }
        }
    }

    void setImageView(){
        Image image = new Image("icon.png");
        post_image_view.setImage(image);
    }

    public void setUsername_lable(String username){
        username_lable.setText(username);
    }


    public void setTag_lable(String tag){
        tag_lable.setText(tag);
    }


    public void setPost_text_(String text){
        post_text_text.setText(text);
    }

    public void setDate_lable(){
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        date_lable.setText(today.toString()+" "+ time.toString());
    }

    public void setPost_image_view(Image image){
        try {
            if (image != null) {
                post_image_view.setImage(image);
            }
        }
        catch (Exception e){
            System.out.println("Error in settings post");
        }
    }

    @FXML
    void seeLarger() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("BigImage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        BigImage bigImage = fxmlLoader.getController();
        bigImage.setBigImageView(post_image_view.getImage());
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.setTitle("");
        newstage.show();
    }

}
