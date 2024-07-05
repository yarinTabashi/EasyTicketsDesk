module com.example.easyticketsdesk {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    opens com.example.easyticketsdesk to javafx.fxml;
    exports com.example.easyticketsdesk;
}