package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductTypeDTO;
import com.aston.aston_project.dto.util.ProductTypeDTOMapping;
import com.aston.aston_project.entity.ProductType;
import com.aston.aston_project.repository.ProductTypeRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeDTOMapping productTypeDTOMapping;

    @Override
    public ProductTypeDTO getById(Long id) {
        return productTypeDTOMapping.entityToDto(
                productTypeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Product type with id " + id + " not found")));
    }

    @Override
    public List<ProductTypeDTO> findByName(String name) {
        return productTypeRepository.findByNameIgnoreCaseContaining(name).stream()
                .map(productTypeDTOMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProductTypeDTO> getAll() {
        return productTypeRepository.findAll().stream()
                .map(productTypeDTOMapping::entityToDto)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long id, ProductTypeDTO dto) {
        Optional<ProductType> optionalProductType = productTypeRepository.findById(id);
        if (optionalProductType.isPresent()) {
            ProductType productType = optionalProductType.get();
            productType.setName(dto.getName());
            productTypeRepository.save(productType);
        } else {
            throw new NotFoundDataException("Product type with id " + id + " not found");
        }
    }

    @Override
    public void create(ProductTypeDTO dto) {
        productTypeRepository.save(productTypeDTOMapping.dtoToEntity(dto));
    }

    @Override
    public void delete(Long id) {
        productTypeRepository.deleteById(id);
    }
}