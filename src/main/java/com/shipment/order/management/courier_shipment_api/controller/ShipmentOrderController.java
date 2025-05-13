package com.shipment.order.management.courier_shipment_api.controller;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import com.shipment.order.management.courier_shipment_api.exception.ShipmentOrderException;
import com.shipment.order.management.courier_shipment_api.service.ShipmentOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipment")
public class ShipmentOrderController {
    private final ShipmentOrderService shipmentOrderService;
    @Autowired
    public ShipmentOrderController(ShipmentOrderService shipmentOrderService){
        this.shipmentOrderService = shipmentOrderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createShipmentOrder(@Valid @RequestBody ShipmentOrderRequestData shipmentOrderRequestData) {
        try {
            return new ResponseEntity<String>(shipmentOrderService.createShipmentOrderRequest(shipmentOrderRequestData), HttpStatus.CREATED);
        } catch (ShipmentOrderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/track-order/{orderId}")
    public ResponseEntity<String> trackShipmentOrder(@PathVariable("orderId") Long orderId) {
        try {
            return new ResponseEntity<String>(shipmentOrderService.getOrderStatus(orderId), HttpStatus.OK);
        } catch (ShipmentOrderException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
