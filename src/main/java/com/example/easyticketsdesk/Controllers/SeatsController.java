package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.SeatComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.Seat;
import com.example.easyticketsdesk.RedisSubscriber;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.List;

public class SeatsController {
    @FXML
    private Label event_name_label, location_and_venue_label, selected_desc_label;
    @FXML
    private Button reserve_btn;
    @FXML
    private MainWindowController mainWindowController;

    // Additional information
    @FXML
    private GridPane seats_gridpane; // Container for displaying seat components
    private List<Seat> seatList; // List to hold seat objects
    private Event event; // Current event object
    private Seat chosenSeat; // Current seat object

    @FXML
    public void initialize(MainWindowController mainWindowController, Event event) {
        this.mainWindowController = mainWindowController;
        this.event = event;

        this.seatList = RequestsUtility.getSeatsMapping(mainWindowController.getJwt(), this.event.getEventId().intValue());
        updateSeatGrid();
        setEventDetails();

        // Initialize the RedisSubscriber
        String channelName = "purchase-channel-" + event.getEventId().toString();
        RedisSubscriber redisSubscriber = new RedisSubscriber("localhost", 6379, channelName);
        Thread subscriberThread = new Thread(redisSubscriber);
        subscriberThread.setDaemon(true);
        subscriberThread.start();
    }

    public void setMainWindowController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    // Initialize the seat grid with SeatComponent instances based on seatList data.
    private void updateSeatGrid() {
        initializeGridPane();

        SeatComponent seatComponent;

        for (Seat seat : seatList) {
            seatComponent = createSeatComponent(seat);
            seatComponent.setUserData(seat); // Store seat data in the component for later use

            seats_gridpane.add(seatComponent, seat.getSeatNumber(), seat.getRowNumber());
        }
    }

    // Set up uniform column and row constraints
    public void initializeGridPane(){
        for (int i = 0; i < 3; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(80);
            seats_gridpane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < 3; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(30);
            seats_gridpane.getRowConstraints().add(rowConstraints);
        }
    }

    // Creates a SeatComponent instance for the given Seat.
    private SeatComponent createSeatComponent(Seat seat) {
        SeatComponent seatComponent = new SeatComponent(seat.getSeatStatus());
        seatComponent.setSeat(seat);
        seatComponent.setSeatsController(this);
        seatComponent.setPrefSize(80, 30);

        return seatComponent;
    }

    public void reserve_clicked(MouseEvent mouseEvent) {
        System.out.println("Reserved clicked");
        boolean succeed = RequestsUtility.reserve(mainWindowController.getJwt(), this.chosenSeat.getId().intValue());
        if (succeed){
            showMessage("Success", "Reservation successful!", Alert.AlertType.INFORMATION);
            mainWindowController.load_dashboard();
        }
        else{
            showMessage("Error", "Reservation failed. Please try again.", Alert.AlertType.ERROR);
        }
    }

    private void showMessage(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Sets the event details on the window.
    public void setEventDetails() {
        this.event_name_label.setText(this.event.getEventName());
        this.location_and_venue_label.setText(this.event.getDateFormat());
    }

    // Sets the chosen seat and displays its details.
    public void setChosenSeat(Seat chosenSeat){
        this.chosenSeat = chosenSeat;
        System.out.println("Seat id: " + chosenSeat.getId() + "(" + chosenSeat.getRowNumber() + "," + chosenSeat.getSeatNumber() + ")");
    }
}
