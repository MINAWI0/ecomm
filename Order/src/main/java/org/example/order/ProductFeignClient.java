package org.example.order;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
@Service
public interface ProductFeignClient {
    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable String id);
    @PutMapping("/api/products/{id}/quantity")
    ProductDto updateQuantityByProductId(@PathVariable String id, @RequestParam Integer quantity);
}