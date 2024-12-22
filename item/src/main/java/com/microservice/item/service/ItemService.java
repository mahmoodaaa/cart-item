package com.microservice.item.service;

import com.microservice.item.error.RecordNotFoundExciption;
import com.microservice.item.model.dto.ItemRequestDTO;
import com.microservice.item.model.dto.ItemResponseDTO;
import com.microservice.item.model.entity.Item;

import java.util.List;
import java.util.NoSuchElementException;

public interface ItemService {

    public ItemResponseDTO createItem(ItemRequestDTO dto) ;
    public List<ItemResponseDTO> getAllItems() ;
    public ItemResponseDTO getItemById(String id) ;
    public ItemResponseDTO updateItem(String id, ItemRequestDTO dto) ;
    public void softDeleteItem(String id) ;
    public void hardDeleteItem(String id) ;
    public List<ItemResponseDTO> getItemsByIds(List<String> itemIds) ;
}
