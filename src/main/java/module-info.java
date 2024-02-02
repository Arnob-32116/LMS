module com.example.loginpagemain {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires javax.mail;


    opens com.example.loginpagemain to javafx.fxml;
    exports com.example.loginpagemain;
}