package com.shipment.order.management.courier_shipment_api.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment_orders")
public class ShipmentOrderRequestData {

    @Id
    private Long orderId;

    @NotNull(message = "Customer mobile no is required.")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number. Must be 10 digits and start with 6-9.")
    private String customerMobileNo;

    @NotNull(message = "Customer Email is required.")
    @Email(message = "Invalid email format.")
    private String customerEmail;

    @NotBlank(message = "Shipping address is required.")
    @Size(max = 255, message = "Shipping address cannot exceed 255 characters.")
    private String shippingAddress;

    private LocalDate shippingDate= LocalDate.now().plusDays(7);

    @NotNull(message = "Order total is required.")
    @DecimalMin(value = "0.01", message = "Order total must be greater than 0.")
    private BigDecimal orderTotal;

    @NotNull(message = "Payment method is required.")
    @Pattern(regexp = "^(CREDIT_CARD|DEBIT_CARD|PAYPAL|BANK_TRANSFER)$", message = "Invalid payment method.")
    private String paymentMethod;

    @NotNull(message = "Order status is required.")
    @Pattern(regexp = "^(PENDING|SHIPPED|DELIVERED|CANCELLED)$", message = "Invalid order status.")
    private String orderStatus;

    private LocalDate createdAt = LocalDate.now();
}