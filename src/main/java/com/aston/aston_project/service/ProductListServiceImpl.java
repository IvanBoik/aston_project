package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductListDTO;
import com.aston.aston_project.dto.util.ProductListDtoMapping;
import com.aston.aston_project.repository.ProductListRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductListServiceImpl {

    private final ProductListRepository productListRepository;
    private final ProductListDtoMapping productListDtoMapping;

    public void addProductList(ProductListDTO dto) {
        productListRepository.save(productListDtoMapping.dtoToEntity(dto));
    }

    public List<ProductListDTO> getAllProductLists() {
       return productListRepository.findAll().stream().map(productListDtoMapping::entityToDto).toList();
    }

    public ProductListDTO getProductListByID(long id) {
        return productListDtoMapping.entityToDto(productListRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("ProductList with id " + id + " not found")));
    }
}
