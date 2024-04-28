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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    ScrollPane newsfeed_scrollpane , section_selection_scrollpane ,  message_scrollpane;;
    @FXML
    TextArea status_text_area ;
    @FXML
    Label username_lable_mainpage,studentid_lable_mainpage;
    @FXML
    Button send_message_button;
    @FXML
    TextField send_message_textfield ;
    @FXML
    TextArea chat_text_area;


    Client c ;
    public ArrayList<VBox> vBoxes = new ArrayList<>();
    private Stack<MenuItem> runningcourseitems = new Stack<>();
    static String status="",status_username="",post_date="",post_tag="";
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
            getposts();

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
        ArrayList<String> current_courses = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        String id = studentid_lable_mainpage.getText();
        current_courses = adminDatabase.get_current_course_by_student(id);
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        for(int i = 0 ; i < current_courses.size() ; i++){
            MenuItem menuItem = new MenuItem(current_courses.get(i));
            menuItems.add(menuItem);
        }
        for(int i = 0 ; i < menuItems.size() ; i++){
            Running_courses_splitmenu.getItems().add(menuItems.get(i));

        }

    }


    @FXML
    public void getposts() throws Exception {
        VBox parent = new VBox();
        ArrayList<ArrayList<String>> info = new ArrayList<>();
        StatusDatabase statusDatabase = new StatusDatabase();
        info = statusDatabase.get_status_infromation();
        for(ArrayList<String> rows : info){
            status_username = rows.get(0);
            status = rows.get(1);
            post_tag = rows.get(2);
            post_date = rows.get(4);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
            VBox vbox = fxmlLoader.load();
            parent.getChildren().add(vbox);
        }
        newsfeed_scrollpane.setContent(parent);
    }

    @FXML
    public void add_section_selection(ActionEvent event) throws Exception{
        getsectionlist();
    }

    @FXML
    public void getsectionlist() throws Exception {
        ArrayList<Integer> undone_courses_index = new ArrayList<Integer>();
        ArrayList<String> not_done_courses_title = new ArrayList<String>();
        ArrayList<String> not_done_courses_credit = new ArrayList<String>();
        ArrayList<String> not_done_courses_name = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        String id = studentid_lable_mainpage.getText();
        undone_courses_index = adminDatabase.get_index_undone_course_by_student(id);
        not_done_courses_title =get_undone_courses_title(undone_courses_index);
        not_done_courses_name = get_undone_courses_name(undone_courses_index);
        not_done_courses_credit = get_undone_courses_credit(undone_courses_index);
        VBox parent = new VBox();
        for(int i = 0 ; i < Math.min(5,not_done_courses_title.size())   ; i++){
            AdminController.course_code = not_done_courses_title.get(i);
            AdminController.course_name = not_done_courses_name.get(i);
            AdminController.credit = not_done_courses_credit.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hBox = fxmlLoader.load();
            parent.getChildren().add(hBox);
            System.out.println(not_done_courses_title.get(i) + " " + not_done_courses_name.get(i) + " " + not_done_courses_credit.get(i));
        }

        section_selection_scrollpane.setContent(parent);

    }

    @FXML
    public void update_status(ActionEvent event) throws Exception{
        String username , date , tag ;
        LoginDatabase loginDatabase = new LoginDatabase();
        status = status_text_area.getText();
        username = loginDatabase.getUsername();
        StatusDatabase statusDatabase = new StatusDatabase(status,"date", "tag" , username);
        statusDatabase.status_information_connection();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
//        VBox vbox = fxmlLoader.load();
//        System.out.println("TEST");
//        newsfeed_scrollpane.setContent(vbox);
        status_text_area.setText("");
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


    @FXML
    public void reload_posts(ActionEvent event) throws Exception{
        getposts();
    }

    private ArrayList<String> get_undone_courses_name(ArrayList<Integer> undone_courses_index){
        ArrayList<String> undone_courses_list = new ArrayList<String>();
        ArrayList<String> all_courses_name = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        all_courses_name = adminDatabase.get_course_name();
        int j = 0 ;
        for(int i = 1  ; i < all_courses_name.size() ; i++){
            if(j>=undone_courses_index.size())
                break;
            if(i == undone_courses_index.get(j)){
                j++;
                undone_courses_list.add(all_courses_name.get(i));
            }
        }

        return undone_courses_list;
    }

    private ArrayList<String> get_undone_courses_title(ArrayList<Integer> undone_courses_index){
        ArrayList<String> undone_courses_title_list = new ArrayList<String>();
        ArrayList<String> all_courses_title = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        all_courses_title = adminDatabase.get_course_title();
        int j = 0 ;
        for(int i = 1  ; i < all_courses_title.size() ; i++){
            if(j>=undone_courses_index.size())
                break;
            if(i == undone_courses_index.get(j)+1){
                j++;
                undone_courses_title_list.add(all_courses_title.get(i));
            }
        }
        return undone_courses_title_list;
    }

    private ArrayList<String> get_undone_courses_credit(ArrayList<Integer> undone_courses_index){
        ArrayList<String> undone_courses_credit_list = new ArrayList<String>();
        ArrayList<String> all_courses_credit = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        all_courses_credit = adminDatabase.get_course_credit();
        int j = 0 ;
        for(int i = 0  ; i < all_courses_credit.size() ; i++){
            if(j>=undone_courses_index.size())
                break;
            if(i == undone_courses_index.get(j)){
                j++;
                undone_courses_credit_list.add(all_courses_credit.get(i));
            }
        }

        return undone_courses_credit_list;
    }



    @FXML
    void startChatting(ActionEvent event) throws IOException{
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        if(!Server.isRunning) {
            executorService.execute(() -> {
                try {
                    ServerSocket serverSocket = new ServerSocket(1234);
                    Server server = new Server(serverSocket);
                    System.out.println("Test");
                    server.startServer();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        executorService.execute(() -> {
            try {
                System.out.println("Enter your Username for the group chat:");
                String username = username_lable_mainpage.getText();
                Socket socket = new Socket("localhost", 1234);
                Client client = new Client(socket, username);
                c = client;
                client.listenForMessage();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @FXML
    void SendMessage() throws IOException{

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        System.out.println(c.getUsername());
            executorService.execute(() -> {
                try {
                    c.sendMessage(send_message_textfield.getText());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });

        chat_text_area.appendText(send_message_textfield.getText());
     //   System.out.println(send_message_textfield.getText());
        }






}
