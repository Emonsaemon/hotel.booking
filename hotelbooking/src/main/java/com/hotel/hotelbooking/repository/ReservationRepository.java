package com.hotel.hotelbooking.repository;

import com.hotel.hotelbooking.model.Reservation;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationsByStartDateAndEndDate(Date start, Date end);
    List<Reservation> findReservationsByRoom(Room room);
    List<Reservation> findReservationsByUser(User user);
}
