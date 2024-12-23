package org.example.order;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;

    public Order createOrder(Order order) {
        System.out.println(order);
        ProductDto product = productFeignClient.getProductById(order.getProductId());
        System.out.println(product);
        System.out.println("after feign");
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Insufficient quantity");
        }
        Integer updatedQuantity = product.getQuantity() - order.getQuantity();
        productFeignClient.updateQuantityByProductId(order.getProductId(), updatedQuantity);
        return orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}