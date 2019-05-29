package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.ReservationDTO;
import com.hotel.hotelbooking.dto.converter.ReservationConverter;
import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.Reservation;
import com.hotel.hotelbooking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin
public class ReservationController {

    private ReservationService reservationService;
    private ReservationConverter reservationConverter;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationConverter reservationConverter) {
        this.reservationService = reservationService;
        this.reservationConverter = reservationConverter;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAll() {
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservationService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservationService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<List<ReservationDTO>> getByReservationId(@PathVariable Long id) {
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservationService.findByRoomId(id)), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReservationDTO>> getByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservationService.findByUserId(id)),
                HttpStatus.OK);
    }
    /*@PutMapping("/update")
    public ResponseEntity<ReservationDTO> update(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.update(reservationConverter.convertToEntity(reservationDTO));
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservation), HttpStatus.ACCEPTED);
    }*/

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> create(@RequestBody ReservationDTO reservationDTO) {
        Reservation newReservation = reservationConverter.convertToEntity(reservationDTO);
        Reservation reservation = reservationService.create(newReservation);
        return new ResponseEntity<>(reservationConverter
                .convertToDTO(reservation), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws ElementNotFoundException {
        reservationService.deleteById(id);
    }

}
