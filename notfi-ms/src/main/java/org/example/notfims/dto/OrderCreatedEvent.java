package org.example.notfims.dto;


import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreatedEvent {
    private Long orderId;
    private Long customerId;

}
