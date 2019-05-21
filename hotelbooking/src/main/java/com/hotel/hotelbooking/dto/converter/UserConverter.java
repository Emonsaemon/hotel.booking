package com.hotel.hotelbooking.dto.converter;

import com.hotel.hotelbooking.dto.UserDTO;
import com.hotel.hotelbooking.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDTO> {

    private ModelMapper mapper;

    @Autowired
    public UserConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User convertToEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
