package org.example.notfims.service;

import org.example.notfims.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "order-created", groupId = "notification-group")
    public void listen(OrderCreatedEvent event) {
        // Process the received event
        System.out.println("Received event: " + event);
        // Extract order ID and customer ID from the event
        Long orderId = event.getOrderId();
        Long customerId = event.getCustomerId();
        // Send notification to the user
        sendNotification(customerId, orderId);
    }
    private void sendNotification(Long customerId, Long orderId) {
        // Implement your notification logic here (e.g., send email, SMS)
        System.out.println("Sending notification to customer " + customerId + " for order " + orderId);
    }
}