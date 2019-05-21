package com.hotel.hotelbooking.repository;

import com.hotel.hotelbooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findRoomByBed(Byte bed);
}
