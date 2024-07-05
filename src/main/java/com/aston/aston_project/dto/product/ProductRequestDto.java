package com.aston.aston_project.dto.product;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDto {
    @NotNull
    @Positive
    private Long id;
    @Positive
    @NotNull
    private Integer count;
    @Size(min = 1)
    private String recipe;

    @Builder
    public ProductRequestDto(Long id, Integer count, String recipe) {
        this.id = id;
        this.count = count;
        this.recipe = recipe;
    }
}
