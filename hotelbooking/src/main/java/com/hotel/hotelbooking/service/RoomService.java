package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public Room findByBed(Byte bed) {
        Optional<Room> room = repository.findRoomByBed(bed);
        if (room.isPresent()) {
            return room.get();
        } else {
            throw new NoSuchElementException("Bed not found!");
        }
    }

    public List<Room> findAll() {
        return repository.findAll();
    }

    public Room create(Room room) {
        room.setId(-1L);
        return repository.save(room);
    }

    public Room findById(Long id) {
        return repository.findById(id).get();
    }

    public Room update(Room room) {
        return repository.save(room);
    }

    public void deleteById(Long id) throws ElementNotFoundException {
        Optional<Room> room = repository.findById(id);
        if (room.isPresent()) {
            repository.delete(room.get());
        } else {
            throw new ElementNotFoundException("Room for deletion not found!");
        }
    }
}
