package com.hotel.hotelbooking.dto.converter;

import com.hotel.hotelbooking.dto.RoomInfoDTO;
import com.hotel.hotelbooking.model.Room;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomInfoConverter implements Converter<Room, RoomInfoDTO> {

    private ModelMapper mapper;
    private org.modelmapper.Converter<List<String>, String> toDTOConverter;
    private PropertyMap<Room, RoomInfoDTO> roomMap;

    @Autowired
    public RoomInfoConverter(ModelMapper mapper) {
        this.mapper = mapper;
        this.toDTOConverter = context -> context.getSource().get(0);
        this.roomMap = new PropertyMap<Room, RoomInfoDTO>() {
            protected void configure() {
                using(toDTOConverter).map(source.getPhoto()).setPhoto(null);
            }
        };
        mapper.addMappings(roomMap);
    }

    @Override
    public RoomInfoDTO convertToDTO(Room room) {
        return mapper.map(room, RoomInfoDTO.class);
    }

    @Override
    public Room convertToEntity(RoomInfoDTO roomInfoDTO) {
        return null;
    }
}
