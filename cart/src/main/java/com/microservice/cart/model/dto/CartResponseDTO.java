package com.microservice.cart.model.dto;

import com.microservice.cart.enums.CartStatus;
import com.microservice.cart.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {

    private String cartId;
    private List<ItemResponseDTO> itemList;
    private CartStatus status;
}
