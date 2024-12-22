package com.microservice.cart.service;

import com.microservice.cart.model.dto.CartReqDTO;
import com.microservice.cart.model.dto.CartResponseDTO;
import com.microservice.cart.model.entity.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {
    public CartResponseDTO createCart() ;
    public CartResponseDTO createCartToItem(String cartId, List<Item>items);
    public CartResponseDTO addItemToCart(String cartId, List<CartReqDTO> itemRequests);
    public CartResponseDTO update(String cartId, List<CartReqDTO> updateDto);
    public CartResponseDTO getCartById(String cartId) ;
    public List<CartResponseDTO> getAllCarts() ;
    public void hardDeleteCart(String cartId);
    public Page<CartResponseDTO> getAllCartsPaginated(int page, int size);




}
