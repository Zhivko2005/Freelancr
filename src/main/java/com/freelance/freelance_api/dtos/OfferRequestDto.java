package com.freelance.freelance_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.SpringVersion;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class OfferRequestDto {
    @NotBlank
    @Size(min=3, max =100, message = "Title must be between 3 and 100 characters")
    private String title;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;

    private Set<Long> CategoryIds;

}
