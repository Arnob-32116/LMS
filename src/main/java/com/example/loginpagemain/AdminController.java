package com.example.loginpagemain;

import com.mysql.cj.log.Log;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AdminController implements Initializable {
    @FXML
    TextField creation_username_textfield , creation_email_textfield , creation_id_textfield , creation_password_textfield , creation_retype_pass_textfield , creation_first_phonenum_textfield , creation_second_phone_textfield;
    @FXML
    Pane admin_section_pane,admin_createuser_pane;
    @FXML
    ScrollPane available_Courses_scrollpane , student_id_scrollpane  ;
    @FXML
    TextField search_student_id_textfield;
    @FXML
    VBox admin_section_vbox , admin_createuser_vbox , admin_creation_vbox ;
    @FXML
    Label submit_done_label;
    @FXML
    Button search_student_button_option , search_faculty_button_option ;
    @FXML
    MFXToggleButton semster_break_togglebutton;
    public static int is_semster_break ;
    public  static String credit , course_code, course_name , static_student_id , static_student_name;
    private String search_type = "student";
    public static Boolean password_match = false;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AdminDatabase adminDatabase = new AdminDatabase();
        is_semster_break = adminDatabase.is_semster_break();
        scheduler.scheduleAtFixedRate(this::toggle_handler, 0, 1, TimeUnit.SECONDS);
        try {
            admin_get_allsection();
            admin_get_student_info();
            student_search_option();
            admin_section_pane.toFront();
            admin_section_pane.setVisible(true);
            admin_createuser_pane.toBack();
            admin_createuser_pane.setVisible(false);
            admin_section_vbox.setStyle("-fx-background-color: black");
            admin_createuser_vbox.setStyle("-fx-background-color: #EC650B");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    void admin_get_allsection() throws Exception {
        ArrayList<String> course_credit = new ArrayList<String>();
        ArrayList<String> course_nameall = new ArrayList<String>();
        ArrayList<String> course_title = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        course_credit = adminDatabase.get_course_credit();
        course_nameall = adminDatabase.get_course_name();
        course_title = adminDatabase.get_course_title();
        VBox parent = new VBox();

        for (int i = 0, j = 1; i < course_title.size() - 1; i++ , j++) {
            credit = course_credit.get(i);
            course_code = course_title.get(j);
            course_name = course_nameall.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hBox = fxmlLoader.load();
            parent.getChildren().add(hBox);
        }
        available_Courses_scrollpane.setContent(parent);
    }

    void admin_get_student_info() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        VBox parent = new VBox();
        for(int i = 0 ; i < student_name.size() ; i++){
            if(!acess_level.get(i).equals("0"))
                continue;
            static_student_name = student_name.get(i);
            static_student_id = student_id.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
            HBox hbox = fxmlLoader.load();
            parent.getChildren().add(hbox);
        }
        student_id_scrollpane.setContent(parent);
    }

    void admin_get_faculty_info() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        VBox parent = new VBox();
        for(int i = 0 ; i < student_name.size() ; i++){
            if(!acess_level.get(i).equals("1"))
                continue;
            static_student_name = student_name.get(i);
            static_student_id = student_id.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
            HBox hbox = fxmlLoader.load();
            parent.getChildren().add(hbox);
        }
        student_id_scrollpane.setContent(parent);
    }

    @FXML
    void student_search_option() throws Exception{
        search_student_id_textfield.setText("Enter Student ID");
        search_type = "student";
        admin_get_student_info();
    }

    @FXML
    void show_all_information() throws Exception{
        if(search_type.equals("student")){
            admin_get_student_info();
        }
        else{
            admin_get_faculty_info();
        }
    }

    @FXML
    void faculty_search_option() throws Exception{
        search_student_id_textfield.setText("Enter Faculty ID");
        admin_get_faculty_info();
        search_type = "faculty";
    }



    @FXML
    void admin_get_search_student_info() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        String search =search_student_id_textfield.getText();
        VBox parent = new VBox();
        student_id_scrollpane.setContent(parent);
        if(search_type.equals("student")) {
            for (int i = 0; i < student_name.size(); i++) {
                if (!acess_level.get(i).equals("0"))
                    continue;
                static_student_name = student_name.get(i);
                static_student_id = student_id.get(i);
                if (static_student_id.contains(search)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
                    HBox hbox = fxmlLoader.load();
                    parent.getChildren().add(hbox);
                }
            }
        }
        else{
            for (int i = 0; i < student_name.size(); i++) {
                if (!acess_level.get(i).equals("1"))
                    continue;
                static_student_name = student_name.get(i);
                static_student_id = student_id.get(i);
                if (static_student_name.contains(search)) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
                    HBox hbox = fxmlLoader.load();
                    parent.getChildren().add(hbox);
                }
            }
        }
        student_id_scrollpane.setContent(parent);
    }


    @FXML
    void clear_text_filed(){
        search_student_id_textfield.setText("");
    }

    VBox get_vboxes() throws Exception{
        VBox all = new VBox();
        all.getChildren().add(get_parent_vbox());
        return all;
//        System.out.println("Test");
//        for(Node node : all.getChildren()){
//            System.out.println("Test2");
//            if(node instanceof VBox){
//                VBox vBox = (VBox) node;
//                for(Node node1 : vBox.getChildren()){
//                    if(node1 instanceof HBox){
//                        HBox hBox = (HBox) node1;
//                        hBox.setStyle("-fx-border-color: blue; -fx-border-width: 5;");
//                        System.out.println("test");
//                    }
//                }
//            }
//        }
    }

    public VBox get_parent_vbox() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        VBox parent = new VBox();
        for(int i = 0 ; i < student_name.size() ; i++){
            if(!acess_level.get(i).equals("0"))
                continue;
            static_student_name = student_name.get(i);
            static_student_id = student_id.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
            HBox hbox = fxmlLoader.load();
            parent.getChildren().add(hbox);
        }
        return parent;
    }


    ArrayList<String> selected_students(){
        ArrayList<String> student_id_selected = new ArrayList<String>();
        VBox vBox = (VBox) student_id_scrollpane.getContent();
        for(Node node : vBox.getChildren()){
            if(node instanceof HBox){
                HBox hBox = (HBox) node;
                Color color =(Color) hBox.getBorder().getStrokes().get(0).getBottomStroke();
                if(color.equals(Color.BLACK)) {
                    for (Node node1 : hBox.getChildren()) {
                        Label label = (Label) node1;
                        if (label.getId().equals("student_id_lable")) {
                            student_id_selected.add(label.getText());

                        }
                    }
                }
            }
        }
        return student_id_selected;
    }

    ArrayList<String> selected_course(){
        ArrayList<String> courses_selected = new ArrayList<String>();
        VBox vBox = (VBox) available_Courses_scrollpane.getContent();
        for(Node node : vBox.getChildren()){
            if(node instanceof HBox){
                HBox hBox = (HBox) node;
                Color color =(Color) hBox.getBorder().getStrokes().get(0).getBottomStroke();
                if(!color.equals(Color.BLACK)) {
                    for (Node node1 : hBox.getChildren()) {
                        if(node1 instanceof Label){
                            Label lable = (Label) node1;
                            if(lable.getId().equals("section_course_code_lable")){
                                courses_selected.add(lable.getText());
                            }
                        }
                    }
                }
            }
        }
        return courses_selected;
    }

    @FXML
    public void add_selected_courses_to_database(){
        ArrayList<String> student_id_from_admin = new ArrayList<String>();
        ArrayList<String> course_from_admin = new ArrayList<String>();
        student_id_from_admin = selected_students();
        course_from_admin = selected_course();
        AdminDatabase adminDatabase = new AdminDatabase();
        adminDatabase.insert_admin_course_selection(student_id_from_admin,course_from_admin);
        if(search_type.equals("student")) {
            adminDatabase.add_evaluation(student_id_from_admin, course_from_admin);
        }
    }

    @FXML
    public void switch_to_section(ActionEvent event){
        admin_section_pane.toFront();
        admin_section_pane.setVisible(true);
        admin_section_vbox.setStyle("-fx-background-color: black");
        admin_createuser_vbox.setStyle("-fx-background-color: #EC650B");
        admin_createuser_pane.toBack();
        admin_createuser_pane.setVisible(false);

    }

    @FXML
    public void switch_to_createuser (ActionEvent event){
        admin_createuser_pane.toFront();
        admin_createuser_pane.setVisible(true);
        admin_createuser_vbox.setStyle("-fx-background-color: black");
        admin_section_vbox.setStyle("-fx-background-color: #EC650B");
        admin_section_pane.toBack();
        admin_section_pane.setVisible(false);
    }

    @FXML
    void startChatting(ActionEvent event){

    }



    ArrayList<String> getFacultyInformation(){
        ArrayList<String> facaulty_info = new ArrayList<String>();
        for(Node node : admin_creation_vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof TextField textField){
                        facaulty_info.add(textField.getText());
                    }
                }
            }
        }
        return facaulty_info;
    }

    @FXML
    void SetFacultyInformation(){
        ArrayList<String> faculty_info = getFacultyInformation();
        Hash hash = new Hash(faculty_info.get(3));
        String password = hash.HashFunction();
        LoginDatabase loginDatabase = new LoginDatabase(faculty_info.get(0),faculty_info.get(2), password , faculty_info.get(5) , faculty_info.get(5), faculty_info.get(6),  faculty_info.get(1) , 1  );
        loginDatabase.Login_Information_connection();
        submit_done_label.setText("A New User Created");
        for(Node node : admin_creation_vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof TextField textField){
                        textField.clear();
                    }
                    else if (node1 instanceof PasswordField passwordField){
                        passwordField.clear();
                    }
                }
            }
        }
    }

    @FXML
    void clear_all_data () throws Exception{
        check_surity();
    }

    private void  check_surity() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Areyousure.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        scene.getStylesheets().add(getClass().getResource("MainPage.css").toExternalForm());
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.setTitle("Sure?");
        newstage.show();
    }

    private void toggle_handler(){
        semster_break_togglebutton.setSelected(is_semster_break != 0);
    }



}
