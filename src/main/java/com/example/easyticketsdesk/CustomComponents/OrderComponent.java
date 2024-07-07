package com.example.easyticketsdesk.CustomComponents;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class OrderComponent extends AnchorPane {
    public OrderComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/easyticketsdesk/gui-fxml/ordercomponent.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(OrderComponent.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
