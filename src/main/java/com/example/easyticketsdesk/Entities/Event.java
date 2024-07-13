package com.example.easyticketsdesk.Entities;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private Long eventId;
    private String eventName;
    private String venue;
    private String description;
    private LocalDateTime date;
    private Category category;

    public Event(){

    }

    public Event(Long eventId, String eventName, String description, String venue, LocalDateTime date, Category category) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public Event(JSONObject jsonObject) throws JSONException {
        this.eventId = jsonObject.getLong("id");
        this.eventName = jsonObject.getString("name");
        this.venue = jsonObject.getString("venue");
        this.description = jsonObject.getString("description");

        // Parse date string using DateTimeFormatter
        String dateStr = jsonObject.getString("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        this.date = LocalDateTime.parse(dateStr, formatter);

        // Parse category (assuming Category class exists and has a constructor that accepts JSONObject)
        JSONObject categoryObject = jsonObject.getJSONObject("category");
        this.category = null;
        //this.category = new Category(categoryObject);
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

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm");
        return this.date.format(formatter);
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
