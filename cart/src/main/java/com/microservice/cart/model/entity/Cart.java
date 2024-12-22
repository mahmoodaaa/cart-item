package com.microservice.cart.model.entity;

import com.microservice.cart.enums.CartStatus;
import com.microservice.cart.model.dto.ItemResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "cart")
public class Cart {
    @Id
    @NotNull private String cartId;
    private List<@Valid Item> itemList = new ArrayList<>();
    private CartStatus status = CartStatus.ACTIVE;

}
