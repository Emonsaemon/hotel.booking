package com.hotel.hotelbooking.dto;

import java.math.BigDecimal;
import java.util.List;

public class RoomDetailsDTO {
    private Long id;
    private Byte floor;
    private Byte roomNumber;
    private List<String> photo;
    private BigDecimal pricePerNight;
    private Byte bed;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getFloor() {
        return floor;
    }

    public void setFloor(Byte floor) {
        this.floor = floor;
    }

    public Byte getNumber() {
        return roomNumber;
    }

    public void setNumber(Byte roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Byte getBed() {
        return bed;
    }

    public void setBed(Byte bed) {
        this.bed = bed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
