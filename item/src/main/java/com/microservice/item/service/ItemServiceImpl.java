package com.microservice.item.service;

import com.microservice.item.error.RecordNotFoundExciption;
import com.microservice.item.model.dto.ItemRequestDTO;
import com.microservice.item.model.dto.ItemResponseDTO;
import com.microservice.item.model.entity.Item;
import com.microservice.item.model.mapper.ItemMapper;
import com.microservice.item.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
  @Autowired
    private ItemRepository itemRepository;
  @Autowired
    private ItemMapper itemMapper;
    public List<ItemResponseDTO> getItemsByIds(List<String> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            throw new IllegalArgumentException("Item IDs list cannot be null or empty");
        }

        // Fetch all items by IDs where isDeleted is false
        List<Item> items = itemRepository.findAllByItemIdInAndIsDeletedFalse(itemIds);

        if (items.isEmpty()) {
            throw new RecordNotFoundExciption("No items found for the provided IDs");
        }

        // Map the list of items to a list of response DTOs
        return items.stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO createItem(ItemRequestDTO dto) {
     try {
    Item item = itemMapper.toEntity(dto);
    item.setDeleted(false);
    Item save = itemRepository.save(item);
    return itemMapper.toResponse(save);

       }
       catch (NoSuchElementException ex){
       throw new RecordNotFoundExciption(String.format("can not add item on database"));
        }
     }
        public List<ItemResponseDTO> getAllItems() {
        try {
            return itemRepository.findAllByIsDeletedFalse()
                    .stream()
                    .map(itemMapper::toResponse)
                    .toList();
        } catch (NoSuchElementException ex) {
            throw new RecordNotFoundExciption(String.format("no found with Item was found in database"));
        }
    }
       public ItemResponseDTO getItemById( String id) {

                      return itemRepository.findById(id)
                    .filter(item->!item.isDeleted())
                    .map(itemMapper::toResponse)
                    .orElseThrow(()-> new RecordNotFoundExciption("item not found"));
        }
        public ItemResponseDTO updateItem(String id, ItemRequestDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundExciption("Item not found"));
        if (item.isDeleted()) throw new RecordNotFoundExciption("Item is deleted");
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        return itemMapper.toResponse(itemRepository.save(item));
    }
        public void softDeleteItem(String id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundExciption("Item not found"));
        item.setDeleted(true);
        itemRepository.save(item);
    }
       public void hardDeleteItem(String id) {
        itemRepository.deleteById(id);
    }
}









