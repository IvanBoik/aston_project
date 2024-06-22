package com.aston.aston_project.service;

import com.aston.aston_project.dto.ProducerDtoResponse;
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
    public ProducerDtoResponse getById(Long id) {
        return producerDtoMapping.entityToDto(
                producerRepository.findById(id)
                        .orElseThrow(() -> new NotFoundDataException("Producer with id " + id + " not found"))
        );
    }

    @Override
    public List<ProducerDtoResponse> getAll() {
        return producerRepository.findAll().stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDtoResponse> findByNameLike(String namePart) {
        return producerRepository.findByNameLike(namePart).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public List<ProducerDtoResponse> findByCountry(Country country) {
        return producerRepository.findByCountry(country).stream()
                .map(producerDtoMapping::entityToDto)
                .toList();
    }

    @Override
    public void add(ProducerDtoResponse dto) {
        producerRepository.save(producerDtoMapping.dtoToEntity(dto));
    }

    @Override
    @Transactional
    public void update(Long id, ProducerDtoResponse dto) {
        Optional<Producer> optionalProducer = producerRepository.findById(id);
        if (optionalProducer.isPresent()) {
            Producer p = optionalProducer.get();
            p.setName(dto.getName());
            Country c = new Country();
            c.setCountry(dto.getCountryName());
            p.setCountry(c);
            producerRepository.save(p);
        } else {
            throw new NotFoundDataException("Producer with id " + id + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        producerRepository.deleteById(id);
    }
}