package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jpedal.parser.shape.F;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GiveResources implements Initializable {
    @FXML
    private Label course_title_resources_lable;
    @FXML
    private TextField material_type_textfield;
    @FXML
    private TextArea text_textarea;
    @FXML
     Button Submit_button;
    @FXML
    HBox attachment_hbox ;
    private String url ="" ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCourseTitle(String course_code){
        course_title_resources_lable.setText(course_code);
    }

    public void SetExistingData(String material_type , String text){
        material_type_textfield.setText(material_type);
        text_textarea.setText(text);
    }

    File selectedFile = null;
    @FXML
    void filechooser() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf", "*.doc"));
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            url = selectedFile.getAbsolutePath();
        }
        catch (Exception e){
            url = "";
            System.out.println(e);
        }
    }

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void setInfoToDatabase(){
            FacultyDatabase facultyDatabase = new FacultyDatabase();
            facultyDatabase.submit_materials(text_textarea.getText() , material_type_textfield.getText() , url , course_title_resources_lable.getText() );
    }

    public void make_submit_unavailable(){
        Submit_button.setVisible(false);
    }

    public void make_addAttachment_unavailable(){
        attachment_hbox.setVisible(false);
    }

    public void setURL(String url){
        this.url = url;
    }
    @FXML
    public void ShowAttachment() {
       // System.out.println(url + "This is url");
        if(!url.isEmpty()) {
            try {
                String pdfFilePath = url; // Replace with the actual file path
                ProcessBuilder processBuilder = new ProcessBuilder("xdg-open", pdfFilePath);
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
