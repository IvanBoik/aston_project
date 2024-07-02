package com.aston.aston_project.mapper;

import com.aston.aston_project.dto.SimpleProductDTO;
import com.aston.aston_project.entity.ProductList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductListMapper {


    @Mapping(target = "id",source = "value.product.id")
    @Mapping(target = "name",source = "value.product.name")
    @Mapping(target = "isPrescriptionRequired",source = "value.product.isPrescriptionRequired")
    @Mapping(target = "count",source = "value.count")
    SimpleProductDTO toSimpleProductDto(ProductList value);
}
