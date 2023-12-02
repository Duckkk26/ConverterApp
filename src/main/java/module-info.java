module com.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens project1 to javafx.fxml;
    exports project1;
}