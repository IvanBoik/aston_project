package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.UserResponseDTO;
import com.aston.aston_project.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponseDTO toDto(User user);
}
