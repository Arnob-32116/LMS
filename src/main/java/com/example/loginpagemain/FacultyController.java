package com.example.loginpagemain;

import com.mysql.cj.log.Log;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FacultyController implements Initializable {
    @FXML
    Button faculty_message_button;
    @FXML
    VBox message_vbox;
    @FXML
    VBox message_pane_vbox , Faculty_Evaluation_vbox ,Faculty_Materials_vbox;
    @FXML
    VBox incoming_message_vbox;
    @FXML
    Label username_lable_mainpage;
    @FXML
    ScrollPane outgoing_evaluation_scrollpane , outgoing_materials_scrollpane ;
    @FXML
    private Pane evaluation_pane , message_pane , materials_pane;
    public static String  Current_Button_Message="" , Current_Material_Button = "";
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService evaluation = Executors.newScheduledThreadPool(1);
    private final ScheduledExecutorService materials = Executors.newScheduledThreadPool(1);
    public static ArrayList<Pair<VBox,AnchorPane>> message_buttons_and_panes = new ArrayList<Pair<VBox,AnchorPane>>();
    public static ArrayList<VBox> evaluation_buttons_and_panes = new ArrayList<VBox>();
    public static ArrayList<VBox> material_buttons_and_panes = new ArrayList<VBox>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginDatabase loginDatabase = new LoginDatabase();
        String username;
        username = loginDatabase.getUsername();
        username_lable_mainpage.setText(username);
        try {
            switch_to_messages();
        }
        catch (Exception e){

        }
        scheduler.scheduleAtFixedRate(this::Message_Button_Handeling, 0, 1, TimeUnit.SECONDS);
        evaluation.scheduleAtFixedRate(this::Evalutation_Button_Handeling, 0, 1, TimeUnit.SECONDS);
        materials.scheduleAtFixedRate(this::Materials_Button_Handeling, 0, 1, TimeUnit.SECONDS);

    }



    int current_index = -1;

    public void Message_Button_Handeling() {
        for (int i = 0; i < message_buttons_and_panes.size(); i++) {
            VBox vBox = message_buttons_and_panes.get(i).getKey();
            for (Node node : vBox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getText().equals(MainPage.Current_Button_Message)) {
                        if(current_index==-1) {
                            AnchorPane anchorPane1 = message_buttons_and_panes.get(i).getValue();
                            Platform.runLater(() -> incoming_message_vbox.getChildren().add(anchorPane1));
                            current_index = i;
                        }
                        else if (current_index != i) {
                            AnchorPane anchorPane1 = message_buttons_and_panes.get(i).getValue();
                            Platform.runLater(() -> {
                                incoming_message_vbox.getChildren().clear();
                                incoming_message_vbox.getChildren().add(anchorPane1);
                            });
                            current_index = i;
                        }

                    }
                    else{
                        vBox.setStyle("-fx-background-color : #FFFFFF");
                    }
                }
            }
        }

    }

    @FXML
    public void switch_to_messages() throws Exception{
        evaluation_pane.setVisible(false);
        message_pane.setVisible(true);
        materials_pane.setVisible(false);
        message_pane.toFront();
        message_pane_vbox.getChildren().clear();
        ArrayList<Pair<String,Integer>> TitleAndPorts = getFacultyTitleAndPort();
        VBox Message_Button_parent = new VBox();
        for(int i = 0 ; i < TitleAndPorts.size() ; i++){
            VBox vBox = new VBox();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageButton.fxml"));
            vBox = fxmlLoader.load();
            MessageButtonController messageButtonController = fxmlLoader.getController();
            messageButtonController.setMessage_button_of_Courses(TitleAndPorts.get(i).getKey());
            Message_Button_parent.getChildren().add(vBox);
            AnchorPane anchorPane = new AnchorPane();
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Chat.fxml"));
            anchorPane = fxmlLoader1.load();
            ChatController chatController = fxmlLoader1.getController();
            chatController.SetPort(TitleAndPorts.get(i).getValue());
            chatController.setLable(TitleAndPorts.get(i).getKey());
            message_buttons_and_panes.add(new Pair<VBox,AnchorPane>(vBox,anchorPane));
        }

        message_pane_vbox.getChildren().add(Message_Button_parent);
        //incoming_message_vbox.getChildren().add(anchorPane);
    }


    ArrayList<Pair<String,Integer>> getFacultyTitleAndPort(){
        ArrayList<Pair<String,Integer>> titlesandports = new ArrayList<Pair<String,Integer>>();
        AdminDatabase adminDatabase = new AdminDatabase();
        LoginDatabase loginDatabase = new LoginDatabase();
        String id = loginDatabase.getStudent_ID();
        ArrayList<String> current_courses ;
        current_courses = adminDatabase.get_current_course_by_student(id);
        ArrayList<Integer> ports = new ArrayList<Integer>();
        MainPageDatabase mainPageDatabase = new MainPageDatabase();
        ports = mainPageDatabase.get_port(current_courses);
        for(int i = 0 ; i < ports.size() ; i++){
            System.out.println(current_courses.get(i));
            titlesandports.add(new Pair<String,Integer>(current_courses.get(i),ports.get(i)));
        }
        return  titlesandports;
    }


    @FXML
    public void switch_to_evaluation() throws Exception{
        System.out.println("Switch to evaluation activated");
        evaluation_pane.setVisible(true);
        message_pane.setVisible(false);
        materials_pane.setVisible(false);
        evaluation_pane.toFront();
        Faculty_Evaluation_vbox.getChildren().clear();
        AdminDatabase adminDatabase = new AdminDatabase();
        LoginDatabase loginDatabase = new LoginDatabase();
        String id = loginDatabase.getStudent_ID();
        ArrayList<String> Titles = adminDatabase.get_current_course_by_student(id);;
        VBox Message_Button_parent = new VBox();
        for(int i = 0 ; i < Titles.size() ; i++){
            VBox vBox = new VBox();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageButton.fxml"));
            vBox = fxmlLoader.load();
            MessageButtonController messageButtonController = fxmlLoader.getController();
            messageButtonController.setMessage_button_of_Courses(Titles.get(i));
            messageButtonController.SetBackgroundColorButton();
            Message_Button_parent.getChildren().add(vBox);
            evaluation_buttons_and_panes.add(vBox);
        }
        Faculty_Evaluation_vbox.getChildren().add(Message_Button_parent);
        //incoming_message_vbox.getChildren().add(anchorPane);
    }

    @FXML
    public void switch_to_materials() throws Exception{
        System.out.println("Switch to materials activated");
        materials_pane.setVisible(true);
        evaluation_pane.setVisible(false);
        message_pane.setVisible(false);
        materials_pane.toFront();
        Faculty_Materials_vbox.getChildren().clear();
        AdminDatabase adminDatabase = new AdminDatabase();
        LoginDatabase loginDatabase = new LoginDatabase();
        String id = loginDatabase.getStudent_ID();
        ArrayList<String> Titles = adminDatabase.get_current_course_by_student(id);;
        VBox Message_Button_parent = new VBox();
        for(int i = 0 ; i < Titles.size() ; i++){
            VBox vBox = new VBox();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageButton.fxml"));
            vBox = fxmlLoader.load();
            MessageButtonController messageButtonController = fxmlLoader.getController();
            messageButtonController.setMessage_button_of_Courses(Titles.get(i));
            messageButtonController.SetBackgroundColorButton();
            Message_Button_parent.getChildren().add(vBox);
            material_buttons_and_panes.add(vBox);
        }
        Faculty_Materials_vbox.getChildren().add(Message_Button_parent);
        //incoming_message_vbox.getChildren().add(anchorPane);
    }

    int current_index1 = - 1;

    public void Evalutation_Button_Handeling(){
        for (int i = 0; i < evaluation_buttons_and_panes.size(); i++) {
            VBox vBox = evaluation_buttons_and_panes.get(i);
            for (Node node : vBox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getText().equals(Current_Button_Message)) {
                        if(current_index1==-1) {
                            FacultyDatabase facultyDatabase = new FacultyDatabase();
                            VBox parent = new VBox() ;
                            try {
                                ArrayList<VBox> evaluationVboxes = facultyDatabase.get_number_information(Current_Button_Message);
                                for(int k = 0 ; k < evaluationVboxes.size() ; k++){
                                    parent.getChildren().add(evaluationVboxes.get(k));
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            Platform.runLater(() -> outgoing_evaluation_scrollpane.setContent(parent));
                            current_index1 = i;
                            System.out.println(Current_Button_Message);
                        }
                        else if (current_index1!= i) {
                            FacultyDatabase facultyDatabase = new FacultyDatabase();
                            VBox parent = new VBox() ;
                            try {
                                ArrayList<VBox> evaluationVboxes = facultyDatabase.get_number_information(Current_Button_Message);
                                for (int k = 0; k < evaluationVboxes.size(); k++) {
                                    parent.getChildren().add(evaluationVboxes.get(k));
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Platform.runLater(() -> {
                               outgoing_evaluation_scrollpane.setContent(parent);
                            });
                            current_index1 = i;
                        }
                    }
                    else{
                        vBox.setStyle("-fx-background-color : #FFFFFF");
                    }
                }
            }
        }

    }

    int current_index2 = -1 ;
    public void Materials_Button_Handeling(){
        for (int i = 0; i < material_buttons_and_panes.size(); i++) {
            VBox vBox = material_buttons_and_panes.get(i);
            for (Node node : vBox.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getText().equals(Current_Button_Message)) {
                        if(current_index2==-1) {
                            FacultyDatabase facultyDatabase = new FacultyDatabase();
                            VBox parent = new VBox() ;
                            try {
                                System.out.println(Current_Button_Message);
                                ArrayList<VBox> materialsVboxes = facultyDatabase.get_materials_information(Current_Button_Message);
                                for(int k = 0 ; k < materialsVboxes.size() ; k++){
                                    parent.getChildren().add(materialsVboxes.get(k));
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                            Platform.runLater(() -> outgoing_materials_scrollpane.setContent(parent));
                            current_index2 = i;
                        }
                        else if (current_index2!= i) {
                            FacultyDatabase facultyDatabase = new FacultyDatabase();
                            VBox parent = new VBox() ;
                            try {
                                ArrayList<VBox> materialsVboxes = facultyDatabase.get_materials_information(Current_Button_Message);
                                for (int k = 0; k < materialsVboxes.size(); k++) {
                                    parent.getChildren().add(materialsVboxes.get(k));
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            Platform.runLater(() -> {
                                outgoing_materials_scrollpane.setContent(parent);
                            });
                            current_index2 = i;
                        }
                    }
                    else{
                        vBox.setStyle("-fx-background-color : #FFFFFF");
                    }
                }
            }
        }

    }

    @FXML
    public void AddMaterials () throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GiveResources.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 656, 200);
        scene.getStylesheets().add(getClass().getResource("MainPage.css").toExternalForm());
        Stage newstage = new Stage();
        GiveResources giveResources = fxmlLoader.getController();
        giveResources.setCourseTitle(Current_Button_Message);
        giveResources.setPrimaryStage(newstage);
        newstage.setScene(scene);
        newstage.setTitle("Course Materials");
        newstage.show();
    }

    @FXML
    void grade_submission(){
        for(Node node : evaluation_pane.getChildren()){
            if(node instanceof ScrollPane scrollPane){
                VBox vBox = (VBox) scrollPane.getContent();
                for(Node node1 : vBox.getChildren()){
                    if(node1 instanceof VBox vBox1){
                        ArrayList<Float> arrayList = new ArrayList<Float>();
                        String Student_id ="";
                        for(Node node2 : vBox1.getChildren()){

                            if(node2 instanceof HBox hBox){
                                for(Node node3 : hBox.getChildren()){
                                    if(node3 instanceof Label label){

                                        if(label.getId()!=null && label.getId().toString().equals("student_id_evaluation")){
                                            Student_id = label.getText();
                                        }
                                    }
                                    if(node3 instanceof TextField textField){
                                       // System.out.println(textField.getText());
                                        arrayList.add(Float.parseFloat(textField.getText()));
                                    }
                                }
                            }

                        }

                        FacultyDatabase facultyDatabase = new FacultyDatabase();
                        facultyDatabase.SubmitGrade(Current_Button_Message , Student_id , arrayList);

                    }
                }
            }
        }


    }

}
