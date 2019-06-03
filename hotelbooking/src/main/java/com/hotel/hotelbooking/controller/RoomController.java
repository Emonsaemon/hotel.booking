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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping("/{id}/images")
    public ResponseEntity<RoomDetailsDTO> addImage(@PathVariable Long id,
            @RequestParam("files") MultipartFile[] files)
            throws IOException {
        return new ResponseEntity<>(roomDetailsConverter.convertToDTO(
                roomService.createImages(files, id)),
                HttpStatus.CREATED);
    }

    @GetMapping("/images/{imageName}")
    public RedirectView redirectToImageGet(@PathVariable String imageName) {
        return new RedirectView("/images/" + imageName);
    }

    @DeleteMapping("/{id}/images")
    public void deleteImageById(@PathVariable Long id)
            throws IOException, NoSuchElementException {
        roomService.deleteImages(id);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<RoomDetailsDTO> updateImageById(
            @PathVariable Long id, @RequestParam("file") MultipartFile[] file)
            throws IOException, NoSuchElementException {
        Room room = roomService.updateImages(id, file);
        return new ResponseEntity<>(roomDetailsConverter.convertToDTO(
                room), HttpStatus.ACCEPTED);
    }
}
