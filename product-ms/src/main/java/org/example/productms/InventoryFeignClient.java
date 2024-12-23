package org.example.productms;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service")
@Service
public interface InventoryFeignClient {
    @GetMapping("/api/inventories/product/{id}")
    InventoryDto getInventoryById(@PathVariable String id);

    @PostMapping("/api/inventories")
    void createInventory(@RequestBody InventoryDto inventoryDto);

    @PutMapping("/api/inventories/{productId}/update-quantity")
    void updateQuantityByProductId(@PathVariable String productId, @RequestBody Integer quantity);

    @DeleteMapping("/api/inventories/{productId}/delete")
    void deleteInventoryByProductId(@PathVariable String productId);


}
