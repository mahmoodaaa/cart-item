package com.microservice.cart.model.mapper;

import com.microservice.cart.enums.CartStatus;
import com.microservice.cart.model.dto.CartReqDTO;
import com.microservice.cart.model.dto.CartResponseDTO;
import com.microservice.cart.model.dto.ItemResponseDTO;
import com.microservice.cart.model.entity.Cart;
import com.microservice.cart.model.entity.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponseDTO toResponse(Cart cart) {
        if (cart == null) {
            return null;
        }
        return CartResponseDTO.builder()
                .cartId(cart.getCartId())
                .itemList(mapItemsToResponse(cart.getItemList()))
                .build();
    }
    public Cart toEntity(CartReqDTO cartRequestDTO) {
        if (cartRequestDTO == null) {
            return null;
        }
        return Cart.builder()
                .cartId(cartRequestDTO.getItemId())
                .itemList(null)
                .build();
    }
    public List<ItemResponseDTO> mapItemsToResponse(List<Item> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(item -> ItemResponseDTO.builder()
                        .itemId(item.getItemId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
    public List<Item> mapRequestsToItems(List<CartReqDTO> requests) {
        if (requests == null) {
            return null;
        }
        return requests.stream()
                .map(request -> Item.builder()
                        .itemId(request.getItemId())
                        .quantity(request.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }


    public CartResponseDTO toResponseCart(Cart cart) {
        if (cart == null) {
            return null;
        }
        return CartResponseDTO.builder()
                .cartId(cart.getCartId())
                .cartId(cart.getItemList().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()).toString())
                .status(cart.getStatus())
                .build();
    }

    // Convert Item to ItemResponseDTO
    public ItemResponseDTO toItemResponse(Item item) {
        if (item == null) {
            return null;
        }
        return ItemResponseDTO.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

}
