package com.aston.aston_project.service;

import com.aston.aston_project.dto.util.ProducerNotFountException;
import com.aston.aston_project.entity.Country;
import com.aston.aston_project.entity.Producer;
import com.aston.aston_project.repository.ProducerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final ProducerRepository producerRepository;

    @Override
    public void add(Producer producer) {
        producerRepository.save(producer);
    }

    @Override
    public Producer getById(Long id) {
        Optional<Producer> optionalProducer = producerRepository.findById(id);
        if (optionalProducer.isPresent()) {
            return optionalProducer.get();
        } else {
            throw new ProducerNotFountException("producer with id " + id + " not found");
        }
    }

    @Override
    public List<Producer> getAll() {
        return producerRepository.findAll();
    }

    public List<Producer> findByNameLike(String namePart) {
        return producerRepository.findByNameLike(namePart);
    }

    public List<Producer> findByCountry(Country country) {
        return producerRepository.findByCountry(country);
    }

    @Override
    @Transactional
    public void update(Long id, Producer producer) {
        Optional<Producer> optionalProducer = producerRepository.findById(id);
        if (optionalProducer.isPresent()) {
            Producer p = optionalProducer.get();
            p.setName(producer.getName());
            p.setCountry(producer.getCountry());
            producerRepository.save(p);
        } else {
            throw new ProducerNotFountException("producer with id " + id + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        producerRepository.deleteById(id);
    }
}