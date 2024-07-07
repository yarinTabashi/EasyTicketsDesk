package com.example.easyticketsdesk.Entities;
import java.util.Date;

public class Order {
    private Long orderId;
    private Event event;
    private double price;
    private Date orderDate;
    private int orderCode; // For generating the QR

    public Order(){

    }

    public Order(Long orderId, Event event, double price, Date orderDate, int orderCode) {
        this.orderId = orderId;
        this.event = event;
        this.price = price;
        this.orderDate = orderDate;
        this.orderCode = orderCode;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }
}
