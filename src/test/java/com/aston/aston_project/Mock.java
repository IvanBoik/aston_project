package com.aston.aston_project;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class Mock {

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }
}