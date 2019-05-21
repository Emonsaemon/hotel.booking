package com.hotel.hotelbooking.dto;

import java.math.BigDecimal;

public class RoomInfoDTO {
    private Long id;
    private String photo;
    private BigDecimal pricePerNight;
    private Byte bed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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
}
