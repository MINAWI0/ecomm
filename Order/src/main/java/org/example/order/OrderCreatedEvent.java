package org.example.order;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreatedEvent {
    private Integer orderId;
    private Integer customerId;
    // Constructor, getters and setters
}