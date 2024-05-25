package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddTagController implements Initializable {
    @FXML
    private VBox tag_filter_vbox;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private int count = 0 ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scheduler.scheduleAtFixedRate(this::is_more_selected, 0, 1, TimeUnit.SECONDS);
    }

    @FXML
    void TagSelectionDone(){
        scheduler.shutdown();
        String tag = "tag";
        for(Node node : tag_filter_vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof CheckBox checkBox){
                        if(checkBox.isSelected()){
                            tag = checkBox.getText();
                        }
                    }
                }
            }
        }
        MainPage.status_tag = tag;
        Stage stage = (Stage) tag_filter_vbox.getScene().getWindow();
        stage.close();
    }
    ArrayList<CheckBox> checkBoxesList = new ArrayList<CheckBox>();
    private void is_more_selected(){
        count = 0 ;
        for(Node node : tag_filter_vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof CheckBox checkBox){
                        if(checkBox.isSelected()){
                            if(!checkBoxesList.contains(checkBox)) {
                                checkBoxesList.add(checkBox);
                            }
                            count++;
                        }
                        if(count>1){
                            checkBoxesList.getFirst().setSelected(false);
                            checkBoxesList.removeFirst();
                            count--;
                        }
                    }
                }
            }
        }
        System.out.println(checkBoxesList.size());
    }
}
