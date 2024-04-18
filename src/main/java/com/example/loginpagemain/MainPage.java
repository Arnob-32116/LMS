package com.example.loginpagemain;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;
import io.github.palexdev.materialfx.controls.MFXComboBox;


public class MainPage implements Initializable {
    @FXML
    Button message_button,newsfeed_button,quiz_button,section_button;
    @FXML
    Pane newsfeed_pane,section_pane,message_pane,quiz_pane;
    @FXML
    VBox newsfeed_vbox,section_vbox, quiz_vbox,message_vbox,section_selection_vbox;
    @FXML
    LineChart<?,?> cgpa_graph,last_five_exam_result_graph,alltime_exam_result_graph;
    @FXML
    PieChart credit_pie , assignment_pie ;
    @FXML
    MFXComboBox<String> tag_box = new MFXComboBox<>();
    @FXML
    MFXComboBox<String> date_box = new MFXComboBox<>();
    @FXML
    SplitMenuButton Running_courses_splitmenu;
    @FXML
    ScrollPane newsfeed_scrollpane;
    @FXML
    TextArea status_text_area;
    @FXML
    Label username_lable_mainpage,studentid_lable_mainpage;

    public ArrayList<VBox> vBoxes = new ArrayList<>();
    private Stack<MenuItem> runningcourseitems = new Stack<>();
    static String status="";
    @Override
    public void initialize(URL location, ResourceBundle resources){
        vBoxes.add(newsfeed_vbox);
        vBoxes.add(section_vbox);
        vBoxes.add(quiz_vbox);
        vBoxes.add(message_vbox);
        vbox_change_colors(newsfeed_vbox);
        tag_box.setPromptText("Choose Tag");
        date_box.setPromptText("Choose Date");
        setusernameinpage();
        setuseridinpage();
        try {
            //getposts();

        }
        catch(Exception e){
            System.out.println(e);
        }
        getTagboxdata();
        getDateboxdata();
        setCgpa_graph();
        setCredit_pie();
        setAssignment_pie();
        setRunning_courses_splitmenu();
        setAlltime_exam_result_graph();
        setLast_five_exam_result_graph();

    }

    void vbox_change_colors(VBox selected ){
            for(VBox vbox : vBoxes){
                if(vbox==selected) {
                    selected.setStyle("-fx-background-color: black");
                    //System.out.println(vbox.getId() + "1");
                }
                else {
                    vbox.setStyle("-fx-background-color: #EC650B");
                    //System.out.println(vbox.getId() + "2");
                }
            }
    }

    @FXML
    public void switch_to_newsfeed(ActionEvent event){
            vbox_change_colors(newsfeed_vbox);
            newsfeed_pane.setVisible(true);
            newsfeed_pane.toFront();
            message_pane.setVisible(false);
            section_pane.setVisible(false);
            quiz_pane.setVisible(false);
    }


    @FXML
    public void switch_to_messages(ActionEvent event){
        vbox_change_colors(message_vbox);
        message_pane.setVisible(true);
        message_pane.toFront();
        section_pane.setVisible(false);
        newsfeed_pane.setVisible(false);
        quiz_pane.setVisible(false);
    }


    @FXML
    public void switch_to_section(ActionEvent event){
        vbox_change_colors(section_vbox);
        section_pane.setVisible(true);
            section_pane.toFront();
            message_pane.setVisible(false);
            newsfeed_pane.setVisible(false);
            quiz_pane.setVisible(false);
    }


    @FXML
    public void switch_to_quiz(ActionEvent event){
        vbox_change_colors(quiz_vbox);
        quiz_pane.setVisible(true);
        quiz_pane.toFront();
        message_pane.setVisible(false);
        newsfeed_pane.setVisible(false);
        section_pane.setVisible(false);
    }

    @FXML
    public void setCgpa_graph(){
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Semester 1", 4.00));
        series.getData().add(new XYChart.Data("Semester 2", 3.50));
        series.getData().add(new XYChart.Data("Semester 3", 4.00));
        cgpa_graph.getData().addAll(series);
    }

    @FXML
    public void setCredit_pie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sem1", 9),
                new PieChart.Data("Sem2", 11),
                new PieChart.Data("Sem3", 14)
        );
        credit_pie.setData(pieChartData);

    }

    @FXML
    public void setAssignment_pie(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("EC", 3),
                new PieChart.Data("DSA1", 2),
                new PieChart.Data("SOC", 1)
        );

        assignment_pie.setData(pieChartData);
    }


    @FXML
    public void setAlltime_exam_result_graph(){
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Exam1", 4.00));
        series.getData().add(new XYChart.Data("Exam2", 3.50));
        series.getData().add(new XYChart.Data("Exam3", 4.00));
        alltime_exam_result_graph.getData().addAll(series);
    }

    @FXML
    public void setLast_five_exam_result_graph(){
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Exam1", 4.00));
        series.getData().add(new XYChart.Data("Exam2", 3.50));
        series.getData().add(new XYChart.Data("Exam3", 4.00));
        last_five_exam_result_graph.getData().addAll(series);
    }

    @FXML
    public void getTagboxdata(){
        tag_box.getItems().addAll("Admission","Academia","Research","Project","Lost and Found","Complain");
        tag_box.setPromptText("Choose Tag");
    }
    @FXML
    public void getDateboxdata(){
        date_box.getItems().addAll("Today","This week","This month","Last year");
        date_box.setPromptText("Choose Date");
    }

    @FXML
    public void setRunning_courses_splitmenu(){
        MenuItem choice1 = new MenuItem("CSE 1115");
        MenuItem choice2 = new MenuItem("CSE 1116");
        MenuItem choice3 = new MenuItem("BDS 1201");

        Running_courses_splitmenu.getItems().addAll(choice1, choice2, choice3);

    }


//    @FXML
//    public void getposts() throws Exception {
//        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
//        VBox parent = new VBox();
//       // parent.setSpacing(20); // Set spacing between VBox elements
//        for (int i = 0; i < 1; i++) {
//            // Create a new VBox instance for each post
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox = fxmlLoader.load();
//            Label label = new Label("HI My name is ");
//            vbox.getChildren().addAll(label);
//            parent.getChildren().add(vbox);
//            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox2 = fxmlLoader2.load();
//            parent.getChildren().add(vbox2);
//
//            FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox3 = fxmlLoader3.load();
//            parent.getChildren().add(vbox3);
//
//            FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox4 = fxmlLoader4.load();
//            parent.getChildren().add(vbox4);
//
//
//            FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox5 = fxmlLoader5.load();
//            parent.getChildren().add(vbox5);
//
//
//            FXMLLoader fxmlLoader6 = new FXMLLoader(getClass().getResource("post.fxml"));
//            VBox vbox6 = fxmlLoader6.load();
//            parent.getChildren().add(vbox6);
//
//            System.out.println("TEST Scrollll");
//        }
//
//        newsfeed_scrollpane.setContent(parent);
//    }

    @FXML
    public void add_section_selection(ActionEvent event) throws Exception{
        getsectionlist();
    }

    @FXML
    public void getsectionlist() throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));

        // parent.setSpacing(20); // Set spacing between VBox elements

            // Create a new VBox instance for each post
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox = fxmlLoader.load();
            //parent.getChildren().add(hbox);
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox2 = fxmlLoader2.load();
            //parent.getChildren().add(hbox2);

            FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox3 = fxmlLoader3.load();
            //parent.getChildren().add(hbox3);

            FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox4 = fxmlLoader4.load();
            //parent.getChildren().add(hbox4);


            FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox5 = fxmlLoader5.load();
            //parent.getChildren().add(hbox5);


            FXMLLoader fxmlLoader6 = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hbox6 = fxmlLoader6.load();
            //parent.getChildren().add(hbox6);

            System.out.println("TEST Scrollll");


        section_selection_vbox.getChildren().addAll(hbox,hbox2);
    }

    @FXML
    public void update_status(ActionEvent event) throws Exception{
        String username , status , date , tag ;
        LoginDatabase loginDatabase = new LoginDatabase();
        status = status_text_area.getText();
        username = loginDatabase.getUsername();
        StatusDatabase statusDatabase = new StatusDatabase(status,"date", "tag" , username);
        statusDatabase.status_information_connection();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
        VBox vbox = fxmlLoader.load();
        System.out.println("TEST");
        newsfeed_scrollpane.setContent(vbox);
    }

    public void setusernameinpage(){
        LoginDatabase loginDatabase = new LoginDatabase();
        username_lable_mainpage.setText(loginDatabase.getUsername());
    }

    public void setuseridinpage(){
        LoginDatabase loginDatabase = new LoginDatabase();
        studentid_lable_mainpage.setText(loginDatabase.getStudent_ID());
    }


    public String get_status_text(){
        return status_text_area.getText();
    }



}
