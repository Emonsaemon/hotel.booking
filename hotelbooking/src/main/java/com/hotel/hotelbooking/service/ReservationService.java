package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.Reservation;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository repository;
    private RoomService roomService;
    private UserService userService;

    @Autowired
    public ReservationService(ReservationRepository repository, RoomService roomService, UserService userService) {
        this.repository = repository;
        this.roomService = roomService;
        this.userService = userService;
    }

    public List<Reservation> findByRoomId(Long id) {
        Room room = roomService.findById(id);
        return repository.findReservationsByRoom(room);
    }

    public List<Reservation> findByUserId(Long id) {
        User user = userService.findById(id);
        return repository.findReservationsByUser(user);
    }

    public Reservation findByStartDateAndEndDate(Date start, Date end) throws ElementNotFoundException {
        Optional<Reservation> reservation = repository.findReservationsByStartDateAndEndDate(start, end);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new NoSuchElementException("Reservation not found!");
        }
    }

    public List<Reservation> findAll() {
        return repository.findAll();
    }

    public Reservation findById(Long id) {
        return repository.findById(id).get();
    }

    public Reservation create(Reservation reservation) {
        reservation.setId(-1L);
        BigDecimal reservationPrice =
                new BigDecimal(reservation.getRoom().getPricePerNight().doubleValue() *
                        (reservation.getEndDate().getTime() - reservation.getStartDate().getTime())/(1000*60*60*24));
        reservation.setPrice(reservationPrice);
        reservation.setUser(userService.getCurrentUser());
        return repository.save(reservation);
    }

    public Reservation update(Reservation reservation) {
        return repository.save(reservation);
    }

    public void deleteById(Long id) throws ElementNotFoundException {
        Optional<Reservation> reservation = repository.findById(id);
        if (reservation.isPresent()) {
            repository.delete(reservation.get());
        } else {
            throw new ElementNotFoundException("Reservation for deletion not found!");
        }
    }
}
