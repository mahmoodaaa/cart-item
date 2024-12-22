package com.microservice.cart.repository;

import com.microservice.cart.model.dto.ItemResponseDTO;
import com.microservice.cart.model.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends MongoRepository<Cart,String> {



}
