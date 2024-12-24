package org.example.order;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;


    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public Order createOrder(Order order) {
        // Log the incoming order
        System.out.println("Creating order: " + order);

        // Fetch product details using Feign Client
        ProductDto product = productFeignClient.getProductById(order.getProductId());
        System.out.println("Fetched product: " + product);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // Check for sufficient product quantity
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Insufficient quantity for product ID: " + order.getProductId());
        }

        // Update product quantity
        Integer updatedQuantity = product.getQuantity() - order.getQuantity();
        productFeignClient.updateQuantityByProductId(order.getProductId(), updatedQuantity);

        // Save the order to the database
        Order savedOrder = orderRepository.save(order);

        // Publish an event to Kafka
        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), savedOrder.getId());
        kafkaTemplate.send("order-created", event);
        System.out.println("Published event to Kafka: " + event);

        return savedOrder;
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