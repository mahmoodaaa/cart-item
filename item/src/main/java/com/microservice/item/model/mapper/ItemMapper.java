package com.microservice.item.model.mapper;

import com.microservice.item.model.dto.ItemRequestDTO;
import com.microservice.item.model.dto.ItemResponseDTO;
import com.microservice.item.model.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item toEntity(ItemRequestDTO itemDTO) {
        return Item.builder()
                .name(itemDTO.getName())
                .price(itemDTO.getPrice())
                .build();
    }
    public ItemResponseDTO toResponse(Item item) {
        return ItemResponseDTO.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
   public List<ItemResponseDTO> toItemResponseList(List<Item> items) {
        return items.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public List<Item> toEntityList(List<ItemRequestDTO> itemRequestDTOs) {
        return itemRequestDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


}

