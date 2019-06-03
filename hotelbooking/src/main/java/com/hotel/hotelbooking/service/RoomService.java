package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomService {

    private RoomRepository repository;
    private FileStorageService fileStorageService;

    @Autowired
    public RoomService(RoomRepository repository,
                       FileStorageService fileStorageService) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
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

    @Transactional
    public Room createImages(MultipartFile[] images, Long roomId)
            throws IOException {
        Room room = findById(roomId);
        if (!room.getPhoto().isEmpty()) {
            throw new IOException("Room images already exist! Try " +
                    "updating them.");
        }
        return addImagesToStorage(room, images);
    }

    @Transactional
    public void deleteImages(Long id) throws IOException,
            NoSuchElementException {
        try {
            Room room = findById(id);
            List<String> images = room.getPhoto();
            room.setPhoto(null);
            update(room);
            for (String image: images) {
                fileStorageService.deleteFile(image);
            }
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Images don't exist!");
        }
    }

    @Transactional
    public Room updateImages(Long id, MultipartFile[] newImages)
            throws IOException {
        Room room = findById(id);
        if (room.getPhoto() != null) {
            deleteImages(id);
        }
        return addImagesToStorage(room, newImages);
    }

    private Room addImagesToStorage(Room room, MultipartFile[] images)
            throws IOException {
        List<String> imagesNames = new ArrayList<>();
        for (MultipartFile image : images) {
            imagesNames.add(fileStorageService.store(image));
        }
        room.setPhoto(imagesNames);
        return update(room);
    }
}
