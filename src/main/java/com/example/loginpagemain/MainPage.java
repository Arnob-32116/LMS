package com.example.loginpagemain;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainPage implements Initializable {
    @FXML
    Button message_button,newsfeed_button,quiz_button,section_button;
    @FXML
    Pane newsfeed_pane,section_pane,message_pane,quiz_pane;
    @FXML
    VBox newsfeed_vbox,section_vbox, quiz_vbox,message_vbox;
    @FXML
    LineChart<?,?> cgpa_graph;
    @FXML
    PieChart credit_pie , assignment_pie ;

    public ArrayList<VBox> vBoxes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBoxes.add(newsfeed_vbox);
        vBoxes.add(section_vbox);
        vBoxes.add(quiz_vbox);
        vBoxes.add(message_vbox);
        vbox_change_colors(newsfeed_vbox);
        setCgpa_graph();
        setCredit_pie();
        setAssignment_pie();
    }

    void vbox_change_colors(VBox selected ){
            for(VBox vbox : vBoxes){
                if(vbox==selected) {
                    selected.setStyle("-fx-background-color: black");
                    System.out.println(vbox.getId() + "1");
                }
                else {
                    vbox.setStyle("-fx-background-color: #EC650B");
                    System.out.println(vbox.getId() + "2");
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




}
