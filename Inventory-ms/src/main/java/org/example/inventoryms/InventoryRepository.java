package org.example.inventoryms;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository  extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findInventoryByProductId(String productId);


    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.productId = :productId")
    void deleteByProductId(@Param("productId")  String productId);

}
