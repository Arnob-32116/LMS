package com.example.loginpagemain;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;


public class MainPage implements Initializable {
    @FXML
    Button message_button,newsfeed_button,quiz_button,section_button;
    @FXML
    Pane newsfeed_pane,section_pane,message_pane,quiz_pane;
    @FXML
    VBox newsfeed_vbox,section_vbox, quiz_vbox,message_vbox,section_selection_vbox , message_pane_vbox ;
    @FXML
    VBox incoming_message_vbox;
    @FXML
    LineChart<?,?> cgpa_graph,last_five_exam_result_graph,alltime_exam_result_graph;
    @FXML
    PieChart credit_pie , assignment_pie ;
    @FXML
    VBox tag_filter_vbox, date_filter_vbox;
    @FXML
    SplitMenuButton Running_courses_splitmenu;
    @FXML
    ScrollPane newsfeed_scrollpane , section_selection_scrollpane ;
    @FXML
    TextArea status_text_area ;
    @FXML
    Label username_lable_mainpage,studentid_lable_mainpage;
    public static String  Current_Button_Message="" ;
    public static AnchorPane current_message_Anchorpane;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static ArrayList<Pair<VBox,AnchorPane>> message_buttons_and_panes = new ArrayList<Pair<VBox,AnchorPane>>();
    public ArrayList<VBox> vBoxes = new ArrayList<>();
    private Stack<MenuItem> runningcourseitems = new Stack<>();
    static String status="",status_username="",post_date="",post_tag="",image_url="";
    @Override
    public void initialize(URL location, ResourceBundle resources){
        vBoxes.add(newsfeed_vbox);
        vBoxes.add(section_vbox);
        vBoxes.add(quiz_vbox);
        vBoxes.add(message_vbox);
        vbox_change_colors(newsfeed_vbox);
        setusernameinpage();
        setuseridinpage();
        scheduler.scheduleAtFixedRate(this::Message_Button_Handeling, 0, 1, TimeUnit.SECONDS);
        try {
            getposts();

        }
        catch(Exception e){
            System.out.println(e);
        }
        get_distance_of_date(19);
        setCgpa_graph();
        setCredit_pie();
        setAssignment_pie();
        setRunning_courses_splitmenu();
        setAlltime_exam_result_graph();
        setLast_five_exam_result_graph();

    }
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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
    public void switch_to_messages(ActionEvent event) throws Exception{
        message_pane_vbox.getChildren().clear();
        vbox_change_colors(message_vbox);
        message_pane.setVisible(true);
        message_pane.toFront();
        section_pane.setVisible(false);
        newsfeed_pane.setVisible(false);
        quiz_pane.setVisible(false);
        ArrayList<Pair<String,Integer>> TitleAndPorts= getTitleAndPort();
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
    int current_index = -1;
    public void Message_Button_Handeling() {
            for (int i = 0; i < message_buttons_and_panes.size(); i++) {
                VBox vBox = message_buttons_and_panes.get(i).getKey();
                for (Node node : vBox.getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getText().equals(Current_Button_Message)) {
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

    Boolean distance_of_date , correct_tag_check ;
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
            image_url = rows.get(5);
            LocalDateTime timestamp = LocalDateTime.parse((String)post_date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate someDate = timestamp.toLocalDate();
            long date = DateandTime.calculateDaysAgo(someDate);
            distance_of_date = get_distance_of_date(date);
            correct_tag_check = is_correct_tag(post_tag);
            if(distance_of_date && correct_tag_check) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
                VBox vbox = fxmlLoader.load();
                parent.getChildren().add(vbox);
            }
        }
        newsfeed_scrollpane.setContent(parent);
    }



    private Boolean get_distance_of_date(long date){
        ArrayList<String> date_filters = new ArrayList<>();
        for(Node node :date_filter_vbox.getChildren()){
            if(node instanceof HBox hbox){
                for(Node node1 : hbox.getChildren()){
                    if(node1 instanceof CheckBox checkBox){
                        if(!date_filters.contains(checkBox.getText())){
                            if(checkBox.isSelected()) {
                                date_filters.add(checkBox.getText());
                            }
                        }
                    }
                }
            }
        }

        for(String st : date_filters){
            if(st.equals("Today") && date==1){
                return true;
            }
            else if(st.equals("Last Week") && date<=7){
                return true;
            }
            else if(st.equals("This Month") && date<=30){
                return true;
            }
            else if(st.equals("Last Year") && date<=365){
                return true;
            }

        }
        return date_filters.isEmpty();
    }

    private Boolean is_correct_tag (String tag){
        String selected ="empty";
        for(Node node : tag_filter_vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof CheckBox checkBox){
                        if(checkBox.isSelected()){
                            selected = checkBox.getText();
                        }
                    }
                }
            }
        }
        return selected.equals(tag) || selected.equals("empty");
    }


    File selectedFile = null;
    @FXML
    void filechooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg","*.png"));
        selectedFile = fileChooser.showOpenDialog(primaryStage);
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
    static String status_tag="tag";
    @FXML
    public void update_status(ActionEvent event) throws Exception{
        String username , date  ,image_url="";
        if(selectedFile!=null) {
             image_url = selectedFile.getAbsolutePath();
        }
        else{
            image_url = "none";
        }
        LoginDatabase loginDatabase = new LoginDatabase();
        status = status_text_area.getText();
        username = loginDatabase.getUsername();

        StatusDatabase statusDatabase = new StatusDatabase(status,"date", status_tag , username , image_url) ;
        statusDatabase.status_information_connection();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("post.fxml"));
//        VBox vbox = fxmlLoader.load();
//        System.out.println("TEST");
//        newsfeed_scrollpane.setContent(vbox);
          status_text_area.setText("");
    }

    @FXML
    void show_preview() throws Exception{
        String username , date  ,image_url="";

        LoginDatabase loginDatabase = new LoginDatabase();
        status = status_text_area.getText();
        username = loginDatabase.getUsername();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("post.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 200);
        Post post = fxmlLoader.getController();
        if(selectedFile!=null) {
            image_url = selectedFile.getAbsolutePath();
            post.setPost_image_view(new Image(new FileInputStream(image_url)));
        }
        else{
            image_url = "none";
        }
        post.setPost_text_(status_text_area.getText());
        post.setTag_lable(status_tag);
        post.setDate_lable();
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.setTitle("Preview");
        newstage.show();

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



    ArrayList<Pair<String,Integer>> getTitleAndPort(){
        ArrayList<Pair<String,Integer>> titlesandports = new ArrayList<Pair<String,Integer>>();
        AdminDatabase adminDatabase = new AdminDatabase();
        String id = studentid_lable_mainpage.getText();
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
    void AddTag() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AddTag.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 200);
        scene.getStylesheets().add(getClass().getResource("MainPage.css").toExternalForm());
        Stage newstage = new Stage();
        newstage.setScene(scene);
        newstage.setTitle("ADD TAG");
        newstage.show();
    }

    @FXML
    void add_section_student_side(){
        ArrayList<String> student_id_from_admin = new ArrayList<String>();
        ArrayList<String> course_from_admin = new ArrayList<String>();
        student_id_from_admin.add(studentid_lable_mainpage.getText());
        course_from_admin = selected_course();
        AdminDatabase adminDatabase = new AdminDatabase();
        adminDatabase.insert_admin_course_selection(student_id_from_admin,course_from_admin);
    }

    private ArrayList<String> selected_course(){
        ArrayList<String> courses_selected = new ArrayList<String>();
        VBox vBox = (VBox)section_selection_scrollpane.getContent();
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













}
