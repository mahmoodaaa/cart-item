package com.microservice.cart.proxy;

import com.microservice.cart.model.dto.ItemResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Item-SERVICE"  ,path = "/item")
public interface ItemProxy {
    @GetMapping("item/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable String id);
    @PostMapping("/fetch-items")
    public ResponseEntity<List<ItemResponseDTO>> getItemsByIds(@RequestBody  List<String> itemIds);
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> getAllItems();
}