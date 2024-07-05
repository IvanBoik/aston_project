package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.dto.UserEmailDto;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.repository.*;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PharmacyProductRepository pharmacyProductRepository;
    private final ProductDtoMapping productDtoMapping;
    private final ProducerRepository producerRepository;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    
    public ProductDtoFullResponse getById(Long id) {
        return productDtoMapping.entityToDtoFull(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"))
        );
    }

    public List<ProductDtoShort> getAll() {
        return productRepository.findAll().stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    public List<ProductDtoShort> findByNameIgnoreCaseContaining(String namePart) {
        return productRepository.findByNameIgnoreCaseContaining(namePart).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }
    
    public List<ProductDtoShort> findByProducer(Long producerId) {
        Producer p = producerRepository.findById(producerId)
                .orElseThrow(() -> new NotFoundDataException("No data for such a producer found"));
        return productRepository.findByProducer(p).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    public List<ProductDtoShort> findByRecipe(Integer recipe) {
        Boolean isPrescriptionRequired;
        isPrescriptionRequired = recipe == 1;
        return productRepository.findAll().stream()
                .filter(p -> p.getIsPrescriptionRequired() == isPrescriptionRequired)
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    public List<ProductDtoShort> findByNameIgnoreCaseContainingAndProducer(String name, Long producerId) {
        Producer p = producerRepository.findById(producerId)
                .orElseThrow(() -> new NotFoundDataException("No data for such a producer found"));
        return productRepository.findByNameIgnoreCaseContainingAndProducer(name, p).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }
    
    public List<ProductDtoShort> findByNameIgnoreCaseContainingAndIsPrescriptionRequired(String name, Integer recipe) {
        boolean isPrescriptionRequired;
        isPrescriptionRequired = recipe == 1;
        return productRepository.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(name, isPrescriptionRequired).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    public List<ProductDtoShort> findByProducerAndIsPrescriptionRequired(Long producerId, Integer recipe) {
        Producer p = producerRepository.findById(producerId)
                .orElseThrow(() -> new NotFoundDataException("No data for such a producer found"));
        boolean isPrescriptionRequired;
        isPrescriptionRequired = recipe == 1;
        return productRepository.findByProducerAndIsPrescriptionRequired(p, isPrescriptionRequired).stream()
                .map(productDtoMapping::entityToDtoShort)
                .toList();
    }

    public void create(ProductRequest dto) {
        productRepository.save(productDtoMapping.dtoToEntity(dto));
    }

    @Transactional
    public void update(Long id, ProductRequest dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));
        if (dto.getName() != null) {
            product.setName(dto.getName());
        }
        if (dto.getType() != null) {
            product.setType(new ProductType(dto.getType()));
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getProducer() != null) {
            product.setProducer(new Producer(dto.getProducer()));
        }
        if (!dto.getAttributesValues().isEmpty()) {
            for (Long att : dto.getAttributesValues().keySet()) {
                product.setAttributesValues(new Attribute(att), new Value(dto.getAttributesValues().get(att)));
            }
        }
        productRepository.save(product);
    }

    public void updateRecipe(Long id, Boolean isRecipe) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));
        product.setIsPrescriptionRequired(isRecipe);
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateCount(Long id, Integer count) {
        PharmacyProduct pharmacyProduct = pharmacyProductRepository.findById(id)
                .orElseThrow(() -> new NotFoundDataException("Product not found"));

        pharmacyProduct.setCount(pharmacyProduct.getCount() + count);
        List<UserEmailDto> emailDtos = userRepository.findAllByWishListContaining(pharmacyProduct.getProduct());

        emailDtos.stream()
                .map(UserEmailDto::getEmail)
                .forEach(email -> mailSenderService.sendUpdatesInWishList(
                        email, pharmacyProduct.getProduct().getName()
                ));
    }
}