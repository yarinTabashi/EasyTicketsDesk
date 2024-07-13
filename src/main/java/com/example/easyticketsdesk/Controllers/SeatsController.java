package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.SeatComponent;
import com.example.easyticketsdesk.Entities.Event;
import com.example.easyticketsdesk.Entities.SeatStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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

    @FXML
    public void initialize() {
        set_seats();
    }

    public void setMainWindowController(MainWindowController mainWindowController){
        this.mainWindowController = mainWindowController;
    }

    public void set_seats() {
        SeatComponent currentSeat;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                SeatStatus seatStatus = SeatStatus.AVAILABLE;
                if (row==1){
                    seatStatus = SeatStatus.RESERVED;
                }
                currentSeat = new SeatComponent(seatStatus);

                // Set preferred size
                currentSeat.setPrefSize(80, 30);

                // Set alignment
                GridPane.setHalignment(currentSeat, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(currentSeat, javafx.geometry.VPos.CENTER);

                // Add to GridPane
                seats_gridpane.add(currentSeat, col, row);
            }
        }
    }

    public void reserve_clicked(MouseEvent mouseEvent) {
        System.out.println("Reserved clicked");
    }

    public void setEventDetails(Event event) {
        this.event_name_label.setText(event.getEventName());
        this.location_and_venue_label.setText(event.getDateFormat());
    }
}
