package com.example.easyticketsdesk.Entities;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long reservationId;
    private LocalDateTime reservationDate;
    private Seat seat;
    private Event event;
    private Integer serialNum;

    public Reservation(Long reservationId, LocalDateTime reservationDate, Seat seat, Event event, Integer serialNum) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.seat = seat;
        this.event = event;
        this.serialNum = serialNum;
    }

    public Reservation(JSONObject jsonObject) throws JSONException {
        this.reservationId = jsonObject.getLong("id");

        // Parse date string using DateTimeFormatter
        String dateStr = jsonObject.getString("reservationDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.reservationDate = LocalDateTime.parse(dateStr, formatter);

        // Parse the seat object first
        JSONObject seatObject = jsonObject.getJSONObject("seat");
        this.seat = new Seat(seatObject); // Assuming Seat has a constructor that accepts JSONObject

        this.serialNum = jsonObject.getInt("serialNum");

        // Now, extract the event object from the seat
        if (seatObject.has("event")) {
            JSONObject eventObject = seatObject.getJSONObject("event");
            this.event = new Event(eventObject); // Assuming Event has a constructor that accepts JSONObject
        }
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public String getReservationDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm");
        return this.reservationDate.format(formatter);
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }
}
