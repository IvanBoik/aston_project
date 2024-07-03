package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.UserAddressDTO;
import com.aston.aston_project.entity.UserAddress;
import org.mapstruct.Mapper;

@Mapper
public interface UserAddressMapper {

    UserAddressDTO toDto(UserAddress userAddress);
    UserAddress toEntity(UserAddressDTO dto);
}
