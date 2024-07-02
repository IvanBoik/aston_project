package com.aston.aston_project.dto.order;

import com.aston.aston_project.dto.SimpleProductDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderWithProductDTO extends OrderDTO{
    private SimpleProductDTO product;
}
