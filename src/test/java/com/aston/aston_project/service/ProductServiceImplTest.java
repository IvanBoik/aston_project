package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;
import com.aston.aston_project.dto.ProductDtoFullResponse;
import com.aston.aston_project.dto.ProductDtoShort;
import com.aston.aston_project.dto.ProductRequest;
import com.aston.aston_project.dto.util.ProductDtoMapping;
import com.aston.aston_project.entity.Attribute;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.entity.ProductType;
import com.aston.aston_project.entity.Value;
import com.aston.aston_project.repository.ProducerRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    ProducerRepository producerRepository;
    @Mock
    ProductDtoMapping productMapper;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void testGetById_WithValidId() {
        Product product = generateProduct();
        ProductDtoFullResponse dto = generateFullDto(product);
        Long id = 100L;

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.entityToDtoFull(product)).thenReturn(dto);

        ProductDtoFullResponse result = productService.getById(id);

        assertEquals(result, dto);

        verify(productRepository).findById(id);
        verify(productMapper).entityToDtoFull(product);
    }

    @Test
    void testGetById_WithNotValidId() {
        Product product = generateProduct();
        ProductDtoFullResponse dto = generateFullDto(product);
        Long id = 200L;

        when(productRepository.findById(id)).thenThrow(new NotFoundDataException("Product not found"));

        assertThatThrownBy(
                () -> productService.getById(200L))
                .isInstanceOf(NotFoundDataException.class);

        verify(productRepository).findById(id);
    }

    @Test
    void getAll() {
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.getAll().size());

        verify(productRepository).findAll();
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByNameIgnoreCaseContaining() {
        String namePart = "ним";

        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);

        when(productRepository.findByNameIgnoreCaseContaining(namePart)).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByNameIgnoreCaseContaining(namePart).size());

        verify(productRepository).findByNameIgnoreCaseContaining(namePart);
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByProducer_WithValidProducer() {
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);
        Long id = 100L;
        Producer producer = new Producer(id);

        when(productRepository.findByProducer(producer)).thenReturn(List.of(product));
        when(producerRepository.findById(id)).thenReturn(Optional.of(producer));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByProducer(id).size());

        verify(productRepository).findByProducer(producer);
        verify(producerRepository).findById(id);
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByProducer_WithNotValidProducer() {
        Long id = 200L;

        when(producerRepository.findById(id)).thenThrow(new NotFoundDataException("No data for such a producer found"));

        assertThatThrownBy(
                () -> productService.findByProducer(id))
                .isInstanceOf(NotFoundDataException.class);

        verify(producerRepository).findById(id);
    }

    @Test
    void findByRecipe() {
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByRecipe(1).size());

        verify(productRepository).findAll();
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByNameIgnoreCaseContainingAndProducer_WithValidProducer() {
        String namePart = "ним";
        Long id = 100L;
        Producer producer = new Producer(id);
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);

        when(producerRepository.findById(id)).thenReturn(Optional.of(producer));
        when(productRepository.findByNameIgnoreCaseContainingAndProducer(namePart, producer)).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByNameIgnoreCaseContainingAndProducer(namePart, id).size());

        verify(producerRepository).findById(id);
        verify(productRepository).findByNameIgnoreCaseContainingAndProducer(namePart, producer);
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByNameIgnoreCaseContainingAndIsPrescriptionRequired() {
        String namePart = "ним";
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);

        when(productRepository.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(namePart, true)).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByNameIgnoreCaseContainingAndIsPrescriptionRequired(namePart, 1).size());

        verify(productRepository).findByNameIgnoreCaseContainingAndIsPrescriptionRequired(namePart, true);
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void findByProducerAndIsPrescriptionRequired_WithValidProducer() {
        Product product = generateProduct();
        ProductDtoShort dto = generateShortDto(product);
        Long id = 100L;
        Producer producer = new Producer(id);

        when(producerRepository.findById(id)).thenReturn(Optional.of(producer));
        when(productRepository.findByProducerAndIsPrescriptionRequired(producer, true)).thenReturn(List.of(product));
        when(productMapper.entityToDtoShort(product)).thenReturn(dto);

        assertEquals(1, productService.findByProducerAndIsPrescriptionRequired(id, 1).size());

        verify(producerRepository).findById(id);
        verify(productRepository).findByProducerAndIsPrescriptionRequired(producer, true);
        verify(productMapper).entityToDtoShort(product);
    }

    @Test
    void create() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();

        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.dtoToEntity(dto)).thenReturn(product);

        productRepository.save(productMapper.dtoToEntity(dto));

        verify(productMapper).dtoToEntity(dto);
        verify(productRepository, only()).save(product);
    }

    @Test
    void update_productName() {
        Product product = generateProduct();
        product.setId(100L);
        ProductRequest dto = generateProductRequest();
        String name = "Нимесулид форте";
        dto.setName(name);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setName(dto.getName());

        when(productRepository.save(product)).thenReturn(product);

        productService.update(100L, dto);

        assertEquals(product.getName(), name);

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void update_productType() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();
        ProductType productType = new ProductType(200L);
        dto.setType(productType.getId());

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setType(productType);

        when(productRepository.save(product)).thenReturn(product);

        productService.update(100L, dto);

        assertEquals(product.getType().getId(), productType.getId());

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void update_productPrice() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();
        BigDecimal price = BigDecimal.valueOf(150);
        dto.setPrice(price);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setPrice(dto.getPrice());

        when(productRepository.save(product)).thenReturn(product);

        productService.update(100L, dto);

        assertEquals(product.getPrice(), price);

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void update_productProducer() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();
        Producer producer = new Producer(200L);
        dto.setProducer(producer.getId());

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setProducer(producer);

        when(productRepository.save(product)).thenReturn(product);

        productService.update(100L, dto);

        assertEquals(product.getProducer().getId(), producer.getId());

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void update_attributesAndValues() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();
        Attribute a = new Attribute(200L);
        a.setAttribute("test attribute");
        Value v = new Value(200L);
        v.setValue("test value");
        Map<Long, Long> map = new HashMap<>();
        map.put(200L, 200L);
        dto.setAttributesValues(map);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setAttributesValues(a, v);

        when(productRepository.save(product)).thenReturn(product);

        productService.update(100L, dto);

        assertTrue(product.getAttributesValues().containsValue(v));
        assertTrue(product.getAttributesValues().containsKey(a));

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void updateRecipe() {
        Product product = generateProduct();
        ProductRequest dto = generateProductRequest();
        Boolean recipe = false;
        dto.setIsPrescriptionRequired(recipe);

        when(productRepository.findById(100L)).thenReturn(Optional.of(product));
        product.setIsPrescriptionRequired(product.getIsPrescriptionRequired());

        when(productRepository.save(product)).thenReturn(product);

        productService.updateRecipe(100L, recipe);

        assertFalse(product.getIsPrescriptionRequired());

        verify(productRepository).findById(100L);
        verify(productRepository).save(product);
    }

    @Test
    void delete() {
        Product product = generateProduct();

        doNothing().when(productRepository).deleteById(product.getId());
        productService.delete(product.getId());

        verify(productRepository).deleteById(product.getId());
    }

    private static Product generateProduct() {
        ProductType productType = new ProductType(100L);
        productType.setName("Таблетки");

        Country c = new Country();
        c.setId(1L);
        c.setCountry("Россия");

        Producer p = new Producer(100L);
        p.setCountry(c);
        p.setName("OZON фармацевтика");

        Attribute a = new Attribute(100L);
        a.setAttribute("Фармакологическая группа");

        Value v = new Value(100L);
        v.setValue("Нестероидный противовоспалительный препарат (НПВП)");

        Map<Attribute, Value> map = new HashMap<>();
        map.put(a, v);

        return Product.builder()
                .id(100L)
                .name("Нимесулид")
                .price(BigDecimal.valueOf(70))
                .type(productType)
                .isPrescriptionRequired(true)
                .producer(p)
                .attributesValues(map)
                .build();
    }

    private ProductDtoFullResponse generateFullDto(Product product) {
        ProductDtoFullResponse dto = new ProductDtoFullResponse();
        dto.setName("Нимесулид");
        dto.setPrice(BigDecimal.valueOf(70));
        dto.setType("Таблетки");
        dto.setIsPrescriptionRequired(true);
        dto.setProducer(new ProducerDtoResponse("OZON фармацевтика", "Россия"));
        Map<String, String> map = new HashMap<>();
        map.put("Фармакологическая группа", "Нестероидный противовоспалительный препарат (НПВП)");
        dto.setAttributesValues(map);
        return dto;
    }

    private ProductDtoShort generateShortDto(Product product) {
        ProductDtoShort dto = new ProductDtoShort();
        dto.setName("Нимесулид");
        dto.setPrice(BigDecimal.valueOf(70));
        dto.setType("Таблетки");
        dto.setIsPrescriptionRequired(true);
        dto.setProducer(new ProducerDtoResponse("OZON фармацевтика", "Россия"));
        return dto;
    }

    private ProductRequest generateProductRequest() {
        ProductRequest dto = new ProductRequest();
        dto.setName("Нимесулид");
        dto.setPrice(BigDecimal.valueOf(70));
        dto.setType(100L);
        dto.setIsPrescriptionRequired(true);
        dto.setProducer(100L);
        Map<Long, Long> map = new HashMap<>();
        map.put(100L, 100L);
        dto.setAttributesValues(map);
        return dto;
    }
}