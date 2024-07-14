package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.OrderComponent;
import com.example.easyticketsdesk.Entities.Reservation;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import java.util.List;

public class MyOrdersController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private VBox ordersContainer;
    private List<Reservation> reservations;

    @FXML
    public void initialize(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        this.reservations = RequestsUtility.getAllReservations(mainWindowController.getJwt());

        OrderComponent newComponent = null;
        for (Reservation reservation : reservations) {
            newComponent = new OrderComponent();
            newComponent.setMainWindowController(mainWindowController, reservation);
            newComponent.setReservationDetails();
            ordersContainer.getChildren().add(newComponent);
        }
    }
}
