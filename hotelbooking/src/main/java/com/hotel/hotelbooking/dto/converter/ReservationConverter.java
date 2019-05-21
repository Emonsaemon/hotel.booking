package com.hotel.hotelbooking.dto.converter;

import com.hotel.hotelbooking.dto.ReservationDTO;
import com.hotel.hotelbooking.dto.UserDTO;
import com.hotel.hotelbooking.model.Reservation;
import com.hotel.hotelbooking.model.Room;
import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.service.RoomService;
import com.hotel.hotelbooking.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter implements Converter<Reservation, ReservationDTO> {

    private ModelMapper mapper;
    private UserService userService;
    private RoomService roomService;
    private org.modelmapper.Converter<User, Long> userToDTOConverter;
    private org.modelmapper.Converter<Long, User> userToEntityConverter;
    private org.modelmapper.Converter<Room, Long> roomToDTOConverter;
    private org.modelmapper.Converter<Long, Room> roomToEntityConverter;
    private PropertyMap<Reservation, ReservationDTO> reservationUserMap;
    private PropertyMap<ReservationDTO, Reservation> reservationUserDTOMap;

    @Autowired
    public ReservationConverter(ModelMapper mapper, UserService userService, RoomService roomService) {
        this.mapper = mapper;
        this.userService = userService;
        this.roomService = roomService;
        this.userToDTOConverter = context -> context.getSource().getId();
        this.roomToDTOConverter = context -> context.getSource().getId();
        this.userToEntityConverter = context -> {
            User user =null;
            try {
                user = userService.findById(context.getSource());
            } catch (Exception e) {}
            finally {
                return user;
            }
        };
        this.roomToEntityConverter = context -> roomService.findById(context.getSource());
        this.reservationUserMap = new PropertyMap<Reservation, ReservationDTO>() {
            protected void configure() {
                using(userToDTOConverter).map(source.getUser()).setUser(null);
                using(roomToDTOConverter).map(source.getRoom()).setRoom(null);
            }
        };
        this.reservationUserDTOMap = new PropertyMap<ReservationDTO, Reservation>() {
            protected void configure() {
                using(userToEntityConverter).map(source.getUser()).setUser(null);
                using(roomToEntityConverter).map(source.getRoom()).setRoom(null);
            }
        };
        mapper.addMappings(reservationUserMap);
        mapper.addMappings(reservationUserDTOMap);
    }

    @Override
    public ReservationDTO convertToDTO(Reservation reservation) {
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        return mapper.map(reservationDTO, Reservation.class);
    }
}
