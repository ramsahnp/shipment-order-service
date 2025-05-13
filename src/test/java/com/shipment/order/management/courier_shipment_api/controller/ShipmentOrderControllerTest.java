package com.shipment.order.management.courier_shipment_api.controller;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import com.shipment.order.management.courier_shipment_api.exception.ShipmentOrderException;
import com.shipment.order.management.courier_shipment_api.service.ShipmentOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ShipmentOrderControllerTest {
    @InjectMocks
    private ShipmentOrderController shipmentOrderController;
    @Mock
    private ShipmentOrderService mockShipmentOrderService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateShipmentOrderSuccess() {
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData();
        when(mockShipmentOrderService.createShipmentOrderRequest(any(ShipmentOrderRequestData.class)))
                .thenReturn("Shipment Order Created Successfully");

        ResponseEntity<String> response = shipmentOrderController.createShipmentOrder(requestData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Shipment Order Created Successfully", response.getBody());
    }

    @Test
    void testCreateShipmentOrderFailure() {
        // Arrange
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData(); // populate fields as needed
        when(mockShipmentOrderService.createShipmentOrderRequest(any(ShipmentOrderRequestData.class)))
                .thenThrow(new ShipmentOrderException("Error creating shipment order"));

        ResponseEntity<String> response = shipmentOrderController.createShipmentOrder(requestData);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error creating shipment order", response.getBody());
    }

    @Test
    void testTrackShipmentOrderSuccess() {
        Long orderId = 123L;
        when(mockShipmentOrderService.getOrderStatus(orderId)).thenReturn("Order is in Transit");

        ResponseEntity<String> response = shipmentOrderController.trackShipmentOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order is in Transit", response.getBody());
    }

    @Test
    void testTrackShipmentOrderFailure() {
        Long orderId = 123L;
        when(mockShipmentOrderService.getOrderStatus(orderId)).thenThrow(new ShipmentOrderException("Order not found"));
        ResponseEntity<String> response = shipmentOrderController.trackShipmentOrder(orderId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
    }
}
