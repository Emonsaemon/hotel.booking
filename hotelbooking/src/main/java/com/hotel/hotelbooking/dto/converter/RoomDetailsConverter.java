package com.hotel.hotelbooking.dto.converter;

import com.hotel.hotelbooking.dto.RoomDetailsDTO;
import com.hotel.hotelbooking.model.Room;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomDetailsConverter implements Converter<Room, RoomDetailsDTO> {

    private ModelMapper mapper;

    @Autowired
    public RoomDetailsConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RoomDetailsDTO convertToDTO(Room room) {
        return mapper.map(room, RoomDetailsDTO.class);
    }

    @Override
    public Room convertToEntity(RoomDetailsDTO roomDetailsDTO) {
        return mapper.map(roomDetailsDTO, Room.class);
    }
}
