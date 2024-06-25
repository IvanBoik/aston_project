package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.AddressResponseDTO;
import com.aston.aston_project.entity.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressResponseDTO toDto(Address address);
}
