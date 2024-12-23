package org.example.inventoryms;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;


    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory getInventoryByProductId(String id) {
        return inventoryRepository.findInventoryByProductId(id).orElse(null);
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
    public Inventory updateQuantityByProductId(String id  , Integer quantity) {
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByProductId(id);
        if(optionalInventory.isPresent()){
            Inventory inventory = optionalInventory.get();
            inventory.setQuantity(quantity);
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteByProductId(id);
    }
}