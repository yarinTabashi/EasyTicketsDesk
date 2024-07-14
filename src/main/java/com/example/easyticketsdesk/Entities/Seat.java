package com.example.easyticketsdesk.Entities;

import org.json.JSONException;
import org.json.JSONObject;

public class Seat {
    private Long id;
    private int rowNumber;
    private int seatNumber;
    private SeatStatus seatStatus;

    public Seat(Long id, int rowNumber, int seatNumber, SeatStatus seatStatus) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
    }

    public Seat(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.rowNumber = jsonObject.getInt("rowNumber");
        this.seatNumber = jsonObject.getInt("seatNumber");
        boolean available = jsonObject.getBoolean("available");
        if (available){
            this.seatStatus = SeatStatus.AVAILABLE;
        }
        else {
            this.seatStatus = SeatStatus.RESERVED;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }
}
