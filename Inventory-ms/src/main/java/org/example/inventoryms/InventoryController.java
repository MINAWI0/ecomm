package org.example.inventoryms;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    //for ms product
    @GetMapping("/product/{id}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable String id) {
        return new ResponseEntity<>(inventoryService.getInventoryByProductId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return new ResponseEntity<>(inventoryService.createInventory(inventory), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/update-quantity")
    public ResponseEntity<Inventory> updateQuantityByProductId(@PathVariable String productId, @RequestBody Integer quantity) {
        return new ResponseEntity<>(inventoryService.updateQuantityByProductId(productId, quantity), HttpStatus.OK);
    }

    // for normal endpoints

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return new ResponseEntity<>(inventoryService.getAllInventories(), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<HttpStatus> deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}