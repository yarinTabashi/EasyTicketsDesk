package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.OrderComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MyOrdersController {
    @FXML
    private VBox ordersContainer;

    @FXML
    public void initialize() {
        // TODO: Use the setDetails() and setQR() methods I have defined in the OrderComponent.
        ordersContainer.getChildren().add(new OrderComponent());
        ordersContainer.getChildren().add(new OrderComponent());
        ordersContainer.getChildren().add(new OrderComponent());
        ordersContainer.getChildren().add(new OrderComponent());
    }
}
