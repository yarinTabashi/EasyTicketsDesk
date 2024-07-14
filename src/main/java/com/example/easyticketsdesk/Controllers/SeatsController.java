package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.SeatComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.Seat;
import com.example.easyticketsdesk.RequestsUtility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.util.List;

public class SeatsController {
    @FXML
    private MainWindowController mainWindowController;
    @FXML
    private Label event_name_label;
    @FXML
    private Label location_and_venue_label;
    @FXML
    private Button reserve_btn;
    @FXML
    private Label selected_desc_label;
    @FXML
    private GridPane seats_gridpane;
    private List<Seat> seatList;
    private Event event;
    private Seat chosenSeat;

    @FXML
    public void initialize(MainWindowController mainWindowController, Event event) {
        this.mainWindowController = mainWindowController;
        this.event = event;

        //set_seats();
        this.seatList = RequestsUtility.getSeatsMapping(mainWindowController.getJwt(), this.event.getEventId().intValue());
        updateSeatGrid();
        setEventDetails();
    }

    public void setMainWindowController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
        //updateSeatGrid();
    }

    private void updateSeatGrid() {
        initializeGridPane();

        SeatComponent seatComponent;

        for (Seat seat : seatList) {
            seatComponent = createSeatComponent(seat);
            seatComponent.setUserData(seat); // Store seat data in the component for later use

            // Add the button to the grid-pane at the appropriate position
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
            System.out.println("Succeed");
        }
        else{
            System.out.println("Unsucceed");
        }
    }

    public void setEventDetails() {
        this.event_name_label.setText(this.event.getEventName());
        this.location_and_venue_label.setText(this.event.getDateFormat());
    }

    public void setChosenSeat(Seat chosenSeat){
        this.chosenSeat = chosenSeat;
        System.out.println("Seat id: " + chosenSeat.getId() + "(" + chosenSeat.getRowNumber() + "," + chosenSeat.getSeatNumber() + ")");
    }
}
