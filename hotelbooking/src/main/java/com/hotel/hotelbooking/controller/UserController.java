package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.dto.UserDTO;
import com.hotel.hotelbooking.dto.converter.UserConverter;
import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.model.UserRole;
import com.hotel.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;
    private UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userConverter
                .convertToDTO(userService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(userConverter
                .convertToDTO(userService.findById(id)), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        User user = userService.update(userConverter.convertToEntity(userDTO));
        return new ResponseEntity<>(userConverter
                .convertToDTO(user), HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createPost(@RequestBody UserDTO userDTO) {
        User user = userService.create(userConverter.convertToEntity(userDTO));
        return new ResponseEntity<>(userConverter
                .convertToDTO(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws ElementNotFoundException {
        userService.deleteById(id);
    }

    @GetMapping("/isadmin")
    public ResponseEntity<UserRole> isAdmin() {
        User user = userService.getCurrentUser();
        UserRole role = (user == null) ? null : user.getRole();
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
