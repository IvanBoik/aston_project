package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.AddressResponseDTO;
import com.aston.aston_project.entity.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderWithAddressDTO extends OrderDTO{
    private AddressResponseDTO addressResponseDTO;
}


