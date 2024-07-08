module com.example.easyticketsdesk {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.zxing;
    requires java.desktop;
    requires com.google.zxing.javase;
    requires com.fasterxml.jackson.databind;
    requires android.json;
    opens com.example.easyticketsdesk to javafx.fxml;
    exports com.example.easyticketsdesk.Controllers;
    opens com.example.easyticketsdesk.Controllers to javafx.fxml;
    exports com.example.easyticketsdesk;
    exports com.example.easyticketsdesk.CustomComponents;
    opens com.example.easyticketsdesk.CustomComponents to javafx.fxml;
    exports com.example.easyticketsdesk.Entities;
    opens com.example.easyticketsdesk.Entities to javafx.fxml;
}