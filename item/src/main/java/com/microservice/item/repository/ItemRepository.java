package com.microservice.item.repository;

import com.microservice.item.model.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {
    List<Item> findByIsDeletedFalse();
    List<Item> findAllByItemIdInAndIsDeletedFalse(List<String> ids);

    List<Item>findAllByItemIdInOrderByItemId(List<String> itemIds);
    List<Item> findAllByIsDeletedFalse();
    List<Item> findByItemIdIn(List<String> itemId);
}
