package com.example.easyticketsdesk.Controllers;
import com.example.easyticketsdesk.CustomComponents.SeatComponent;
import com.example.easyticketsdesk.Entities.SeatStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SeatsController {
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
        set_event_data();
        set_seats();
    }

    public void set_event_data(){
//        this.event_name_label = "";
//        this.location_and_venue_label = "";
    }

    public void set_seats() {
        // Example: Adding CustomCategory objects to GridPane
        SeatComponent currentSeat;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                SeatStatus seatStatus = SeatStatus.AVAILABLE;
                if (row==1){
                    seatStatus = SeatStatus.RESERVED;
                }
                 currentSeat = new SeatComponent(seatStatus);

                // Set preferred size (adjust as needed)
                currentSeat.setPrefSize(80, 30);

                // Set alignment (adjust as needed)
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
}
