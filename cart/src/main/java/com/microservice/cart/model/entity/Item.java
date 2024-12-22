package com.microservice.cart.model.entity;

import com.microservice.cart.enums.CartStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @NotNull
    private String itemId;
    @NotEmpty
    private String name;
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than 0")
    private Double price;
   private Integer quantity;
   @Builder.Default
    private CartStatus itemStatus = CartStatus.ACTIVE;
}
