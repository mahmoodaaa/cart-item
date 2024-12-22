package com.microservice.item.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "item")
public class Item {
    @Id
    @NotNull private String itemId;
    @NotEmpty private String name;
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than 0")
    private Double price;
    private boolean isDeleted;

}
