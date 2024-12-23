package org.example.productms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryFeignClient inventoryFeignClient;

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        InventoryDto inventoryRequest = new InventoryDto();
        inventoryRequest.setProductId(newProduct.getId());
        inventoryRequest.setQuantity(product.getQuantity());
        inventoryFeignClient.createInventory(inventoryRequest);
        return newProduct;
    }
    public Product getProductById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return null;
        }
        product.setQuantity(inventoryFeignClient.getInventoryById(id).getQuantity());
        return product;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(Optional.ofNullable(product.getName()).orElse(existingProduct.getName()));
            existingProduct.setDescription(Optional.ofNullable(product.getDescription()).orElse(existingProduct.getDescription()));
            existingProduct.setPrice(Optional.ofNullable(product.getPrice()).orElse(existingProduct.getPrice()));
            Integer updatedQuantity = Optional.ofNullable(product.getQuantity()).orElse(existingProduct.getQuantity());
            // Only call the Feign client if the quantity has changed
            if (!updatedQuantity.equals(existingProduct.getQuantity())) {
                inventoryFeignClient.updateQuantityByProductId(id, updatedQuantity); // Update inventory if quantity has changed
                existingProduct.setQuantity(updatedQuantity); // Update product quantity after inventory update
                productRepository.save(existingProduct); // Save product with updated quantity
            } else {
                // If quantity hasn't changed, just save the product
                productRepository.save(existingProduct);
            }
            return existingProduct;
        } else {
            return null;
        }
    }
    public Product updateProductQuantity(String id,Integer quantity) {
        Product existingProduct = getProductById(id);
        if (existingProduct == null) return null;
        Integer updatedQuantity = Optional.ofNullable(quantity).orElse(existingProduct.getQuantity());
        existingProduct.setQuantity(updatedQuantity); // Update product quantity locally
        inventoryFeignClient.updateQuantityByProductId(id, updatedQuantity); // Update product quantity in other service
        return existingProduct;
    }



    public void deleteProduct(String id) {
        inventoryFeignClient.deleteInventoryByProductId(id);
        productRepository.deleteById(id);
    }
}
