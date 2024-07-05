package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.AddressResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OrderWithAddressDTO extends OrderDTO{
    private AddressResponseDTO address;
}


