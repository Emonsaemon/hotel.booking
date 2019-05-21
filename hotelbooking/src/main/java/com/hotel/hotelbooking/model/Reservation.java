package com.hotel.hotelbooking.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name="user_id", nullable=false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name="room_id", nullable=false)
    private Room room;

    @NotNull
    @Column(nullable = false, name="start_date")
    private Date startDate;

    @NotNull
    @Column(nullable = false, name="end_date")
    private Date endDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    public Reservation() {
    }

    public Reservation(@NotNull User user,
                       @NotNull Room room,
                       @NotNull Date startDate,
                       @NotNull Date endDate,
                       @NotNull BigDecimal price) {
        this.user = user;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
