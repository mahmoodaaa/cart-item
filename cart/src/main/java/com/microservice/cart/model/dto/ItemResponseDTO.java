package com.microservice.cart.model.dto;

import com.microservice.cart.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDTO {

    private String itemId;
    private String name;
    private Double price;
    private Integer quantity;
    private CartStatus itemStatus = CartStatus.ACTIVE;

}
