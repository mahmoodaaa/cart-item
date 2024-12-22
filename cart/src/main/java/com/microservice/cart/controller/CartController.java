package com.microservice.cart.controller;

import com.microservice.cart.model.dto.CartReqDTO;
import com.microservice.cart.model.dto.CartResponseDTO;
import com.microservice.cart.model.entity.Item;
import com.microservice.cart.service.CartServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;
    @PostMapping("/add-cart")
    public ResponseEntity<CartResponseDTO> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }
    @PostMapping("/{cartId}/create-to-Item")
    public CartResponseDTO createCartToItem( @PathVariable String cartId ,@Valid @RequestBody List<Item>items){
        return ResponseEntity.ok(cartService.createCartToItem(cartId,items)).getBody();
    }
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartResponseDTO> addItemsToCart(@PathVariable String cartId, @Valid @RequestBody List<CartReqDTO> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new IllegalArgumentException("Item requests cannot be null or empty");
        }
        return ResponseEntity.ok(cartService.addItemToCart(cartId, itemRequests));
    }
    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> update(@PathVariable String cartId,
                                                  @Valid @RequestBody List<CartReqDTO> dto){
                return ResponseEntity.ok(cartService.update(cartId,dto));
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable String cartId) {
        return ResponseEntity.ok(cartService.getCartById(cartId));

    }
   @GetMapping
   public ResponseEntity<List<CartResponseDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());

    }
    @DeleteMapping("hard/{cartId}")
    public  ResponseEntity<Void>hardDeleteCart(@PathVariable String cartId) {
         cartService.hardDeleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CartResponseDTO>softDelete(@PathVariable String cartId){

         return ResponseEntity.ok(cartService.softDeleteCart(cartId));
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<CartResponseDTO>> getAllCartsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(cartService.getAllCartsPaginated(page,size));


    }


}
