package com.aston.aston_project.controller;

import com.aston.aston_project.entity.Producer;
import lombok.AllArgsConstructor;
import com.aston.aston_project.entity.Product;
import com.aston.aston_project.dto.*;
import com.aston.aston_project.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor

public final class ProductController {
    public ProducerService producerService;

    @PostMapping
    public ResponseEntity<Producer> create(@RequestBody ProducerDto dto){
        producerService.add(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Producer>> readAll(){
        return new ResponseEntity<>(producerService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestBody Producer producer, @PathVariable long id){
        producerService.update(id,producer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable long id){
        producerService.delete(id);
        return HttpStatus.OK;
    }

}
