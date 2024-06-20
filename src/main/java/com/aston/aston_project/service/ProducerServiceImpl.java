package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDto;
import com.aston.aston_project.dto.util.ProducerDtoMapping;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.repository.ProducerRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;
    private final ProducerDtoMapping producerDtoMapping;

    @Override
    public ProducerDto getById(Long id) {
        return producerDtoMapping.entityToDto(
                producerRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Пользователь не найден"))
        );
    }

    @Override
    public List<ProducerDto> getAll() {
        return producerRepository.findAll().stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDto> findByNameLike(String namePart) {
        return producerRepository.findByNameLike(namePart).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDto> findByCountry(Country country) {
        return producerRepository.findByCountry(country).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public void add(ProducerDto dto) {
        producerRepository.save(producerDtoMapping.dtoToEntity(dto));
    }

    @Override
    @Transactional
    public void update(Long id, ProducerDto dto) {
        Optional<Producer> optionalProducer = producerRepository.findById(id);
        if (optionalProducer.isPresent()) {
            Producer p = optionalProducer.get();
            p.setName(dto.getName());
            Country c = new Country();
            c.setCountry(dto.getCountryName());
            p.setCountry(c);
            producerRepository.save(p);
        } else {
            throw new NotFoundDataException("producer with id " + id + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        producerRepository.deleteById(id);
    }
}