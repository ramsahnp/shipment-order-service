package com.shipment.order.management.courier_shipment_api.service;
import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import com.shipment.order.management.courier_shipment_api.exception.ShipmentOrderException;
import com.shipment.order.management.courier_shipment_api.repository.ShipmentOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ShipmentOrderServiceTest {
    @InjectMocks
    private ShipmentOrderService shipmentOrderService;
    @Mock
    private ShipmentOrderRepository shipmentOrderRepository;
    private ShipmentOrderRequestData requestData;

    @BeforeEach
    void setUp() {
        requestData = new ShipmentOrderRequestData();
        requestData.setOrderId(123L);
        requestData.setOrderTotal(new BigDecimal("100.00"));
        requestData.setOrderStatus("PENDING");
        requestData.setCustomerMobileNo("0123456789");
        requestData.setCustomerEmail("abc@gmail.com");
        requestData.setShippingAddress("abc address");
        MockitoAnnotations.openMocks(this);  // Initializes mock objects
    }

    @Test
    void testCreateShipmentOrderRequest_Success() throws ShipmentOrderException {
        Mockito.when(shipmentOrderRepository.findById(123L)).thenReturn(Optional.empty());
        Mockito.when(shipmentOrderRepository.save(any(ShipmentOrderRequestData.class))).thenReturn(requestData);
        String response = shipmentOrderService.createShipmentOrderRequest(requestData);
        assertNotNull(response);
    }
    @Test
    void testGetOrderStatus_Success() {
        Long orderId = 123L;
        ShipmentOrderRequestData orderData = new ShipmentOrderRequestData();
        orderData.setOrderId(orderId);
        orderData.setOrderStatus("Shipped");
        Mockito.when(shipmentOrderRepository.findById(orderId)).thenReturn(Optional.of(orderData));
        String status = shipmentOrderService.getOrderStatus(orderId);
        assertEquals("Shipped", status);
    }

    @Test
    void testGetOrderStatus_OrderNotFound() {
        Long orderId = 123L;
        Mockito.when(shipmentOrderRepository.findById(orderId)).thenReturn(Optional.empty());
        ShipmentOrderException exception = assertThrows(ShipmentOrderException.class, () -> {
            shipmentOrderService.getOrderStatus(orderId);
        });
        assertEquals("Invalid shipment order data request", exception.getMessage());
    }
}
