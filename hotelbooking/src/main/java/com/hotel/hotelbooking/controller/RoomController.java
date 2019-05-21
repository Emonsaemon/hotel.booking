package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.RoomDetailsDTO;
import com.hotel.hotelbooking.dto.RoomInfoDTO;
import com.hotel.hotelbooking.dto.converter.RoomDetailsConverter;
import com.hotel.hotelbooking.dto.converter.RoomInfoConverter;
import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin
public class RoomController {

    private RoomService roomService;
    private RoomInfoConverter roomInfoConverter;
    private RoomDetailsConverter roomDetailsConverter;

    @Autowired
    public RoomController(RoomService roomService, RoomInfoConverter roomInfoConverter, RoomDetailsConverter roomDetailsConverter) {
        this.roomService = roomService;
        this.roomInfoConverter = roomInfoConverter;
        this.roomDetailsConverter = roomDetailsConverter;
    }

    @GetMapping
    public ResponseEntity<List<RoomInfoDTO>> getAll() {
        return new ResponseEntity<>(roomInfoConverter
                .convertToDTO(roomService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(roomDetailsConverter
                .convertToDTO(roomService.findById(id)), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<RoomDetailsDTO> update(@RequestBody RoomDetailsDTO roomDetailsDTO) {
        Room room = roomService.update(roomDetailsConverter.convertToEntity(roomDetailsDTO));
        return new ResponseEntity<>(roomDetailsConverter
                .convertToDTO(room), HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDetailsDTO> create(@RequestBody RoomDetailsDTO roomDetailsDTO) {
        Room room = roomService.create(roomDetailsConverter.convertToEntity(roomDetailsDTO));
        return new ResponseEntity<>(roomDetailsConverter
                .convertToDTO(room), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws ElementNotFoundException {
        roomService.deleteById(id);
    }
}
