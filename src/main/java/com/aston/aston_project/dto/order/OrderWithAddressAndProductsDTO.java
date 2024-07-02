package com.aston.aston_project.dto.order;


import com.aston.aston_project.dto.SimpleProductDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderWithAddressAndProductsDTO extends OrderWithAddressDTO {
    private List<SimpleProductDTO> products;
}
