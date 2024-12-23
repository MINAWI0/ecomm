package org.example.productms;


import lombok.Data;

@Data
public class InventoryDto {
    private String productId;
    private Integer quantity;
}
