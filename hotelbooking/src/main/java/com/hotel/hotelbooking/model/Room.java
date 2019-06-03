package com.hotel.hotelbooking.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Byte floor;

    @NotNull
    @Column(nullable = false)
    private Byte roomNumber;

    @Column(nullable = false)
    @ElementCollection
    private List<String> photo;

    @NotNull
    @Column(nullable = false, name="price_per_night")
    private BigDecimal pricePerNight;

    @NotNull
    @Column(nullable = false)
    private Byte bed;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column
    @OneToMany(mappedBy="room")
    private List<Reservation> reservations;

    public Room() {
    }

    public Room(@NotNull Byte floor,
                @NotNull Byte roomNumber,
                List<String> photo,
                @NotNull BigDecimal pricePerNight,
                @NotNull Byte bed,
                @NotBlank String description,
                List<Reservation> reservations) {
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.photo = photo;
        this.pricePerNight = pricePerNight;
        this.bed = bed;
        this.description = description;
        this.reservations = reservations;
    }

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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
