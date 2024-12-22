package com.microservice.cart.service;

import com.microservice.cart.enums.CartStatus;
import com.microservice.cart.error.RecordNotFoundExciption;
import com.microservice.cart.model.dto.CartReqDTO;
import com.microservice.cart.model.dto.CartResponseDTO;
import com.microservice.cart.model.dto.ItemResponseDTO;
import com.microservice.cart.model.entity.Cart;
import com.microservice.cart.model.entity.Item;
import com.microservice.cart.model.mapper.CartMapper;
import com.microservice.cart.proxy.ItemProxy;
import com.microservice.cart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemProxy itemProxy;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartRepository cartRepository;

    public CartResponseDTO createCart() {
        try {
            Cart cart = Cart.builder()
                    .itemList(new ArrayList<>())
                    .build();
            return cartMapper.toResponse(cartRepository.save(cart));
        } catch (NoSuchElementException ex) {
            throw new RecordNotFoundExciption(String.format("no found with Item was found in database"));
        }
    }
    public CartResponseDTO createCartToItem(String cartId,List<Item>items){

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }

       Cart cart = cartRepository.findById(cartId)
               .orElseThrow(()-> new RecordNotFoundExciption("can not found id  " + cartId));
        cart.setItemList(items);

        return cartMapper.toResponseCart(cartRepository.save(cart));
    }
    public CartResponseDTO addItemToCart(String cartId, List<CartReqDTO> itemRequests) {
        log.info("Adding items to cart with ID: {}", cartId);
        log.info("Item Requests: {}", itemRequests);

        List<String> itemIds = itemRequests.stream()
                .map(CartReqDTO::getItemId)
                .collect(Collectors.toList());

        log.info("Fetching item details for IDs: {}", itemIds);

        List<ItemResponseDTO> itemDetails = itemProxy.getItemsByIds(itemIds).getBody();

        if (itemDetails == null || itemDetails.isEmpty()) {
            log.error("Items not found in Item Service for IDs: {}", itemIds);
            throw new RecordNotFoundExciption("Items not found in Item Service");
        }

        log.info("Item Details fetched: {}", itemDetails);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.error("Cart not found with ID: {}", cartId);
                    return new RecordNotFoundExciption("Cart not found");
                });

        log.info("Cart fetched: {}", cart);

        for (CartReqDTO req : itemRequests) {
            ItemResponseDTO itemDetail = itemDetails.stream()
                    .filter(detail -> detail.getItemId().equals(req.getItemId()))
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Item not found in response for ID: {}", req.getItemId());
                        return new RecordNotFoundExciption("Item not found: " + req.getItemId());
                    });

            Optional<Item> existingItem = cart.getItemList().stream()
                    .filter(item -> item.getItemId().equals(req.getItemId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                log.info("Updating quantity for existing item: {}", existingItem.get());
                existingItem.get().setQuantity(existingItem.get().getQuantity() + req.getQuantity());
            } else {
                log.info("Adding new item to cart: {}", itemDetail);
                cart.getItemList().add(Item.builder()
                        .itemId(itemDetail.getItemId())
                        .name(itemDetail.getName())
                        .price(itemDetail.getPrice())
                        .quantity(req.getQuantity())
                        .build());
            }
        }

        log.info("Saving updated cart: {}", cart);
        return cartMapper.toResponseCart(cartRepository.save(cart));
    }

    public CartResponseDTO update(String cartId, List<CartReqDTO> updateDto) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                new RecordNotFoundExciption("Cart not found"));

        for (CartReqDTO requestDto : updateDto) {
            Item excitingItem = cart.getItemList()
                    .stream()
                    .filter(item -> item.getItemId().equals(requestDto.getItemId()))
                    .findFirst()
                    .orElse(null);
            if (excitingItem != null) {

                excitingItem.setQuantity(requestDto.getQuantity());
            } else {
                ResponseEntity<ItemResponseDTO> itemResponse = itemProxy.getItemById(requestDto.getItemId());
                if (itemResponse == null) {
                    throw new RuntimeException("Item not found in Item Service");

                }
                cart.getItemList().add(
                        Item.builder()
                                .itemId(requestDto.getItemId())
                                .quantity(requestDto.getQuantity() != null ? requestDto.getQuantity() : 1)
                                .itemStatus(CartStatus.ACTIVE)
                                .build()
                );
            }
        }
        Cart save = cartRepository.save(cart);
        return cartMapper.toResponseCart(save);
    }

    public CartResponseDTO getCartById(String cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RecordNotFoundExciption("Cart not found"));
        if (cart.getStatus() == CartStatus.DELETED) {
            throw new RecordNotFoundExciption("Cart is deleted");
        }
        return cartMapper.toResponseCart(cart);
    }

    public List<CartResponseDTO> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toResponseCart)
                .toList();
    }

    private void validateItemExists(String itemId) {
        try {
            itemProxy.getItemById(itemId);
        } catch (NoSuchElementException ex) {
            throw new RecordNotFoundExciption(String.format("no found Item Exists"));
        }
    }

    public void hardDeleteCart(String cartId) {

        cartRepository.deleteById(cartId);
    }
    public CartResponseDTO softDeleteCart(String cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RecordNotFoundExciption("cart not found"));
        cart.setStatus(CartStatus.DELETED);
        Cart save = cartRepository.save(cart);
        return this.cartMapper.toResponseCart(save);
    }

    public Page<CartResponseDTO> getAllCartsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // PageRequest handles pagination
        Page<Cart> cartsPage = cartRepository.findAll(pageable); // Retrieves a page of carts
        return cartsPage.map(cartMapper::toResponseCart);
    }
}



