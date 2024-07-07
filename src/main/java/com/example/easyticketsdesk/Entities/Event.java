package com.example.easyticketsdesk.Entities;
import java.util.Date;

public class Event {
    private Long eventId;
    private String eventName;
    private String venue;
    private Date date;
    private Category category;

    public Event(){

    }

    public Event(Long eventId, String eventName, String venue, Date date, Category category) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
        this.category = category;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
