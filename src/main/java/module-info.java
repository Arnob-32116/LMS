module com.example.loginpagemain {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;


    opens com.example.loginpagemain to javafx.fxml;
    exports com.example.loginpagemain;
}