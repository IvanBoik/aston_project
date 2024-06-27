package com.aston.aston_project.feign.dto;

import lombok.Data;

@Data
public class CovidResponse {
    private String country;
    private Long cases;
    private Long deaths;
    private Long todayCases;
    private Long recovered;
    private Long active;
    private Long tests;
}