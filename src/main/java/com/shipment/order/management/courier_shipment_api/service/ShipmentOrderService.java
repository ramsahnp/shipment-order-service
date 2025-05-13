package com.shipment.order.management.courier_shipment_api.service;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import com.shipment.order.management.courier_shipment_api.exception.ShipmentOrderException;
import com.shipment.order.management.courier_shipment_api.repository.ShipmentOrderRepository;
import com.shipment.order.management.courier_shipment_api.utils.ShipmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShipmentOrderService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentOrderService.class);
    private final ShipmentOrderRepository shipmentOrderRepository;
    private final ShipmentUtil shipmentUtil;

    @Autowired
    public ShipmentOrderService(ShipmentOrderRepository shipmentOrderRepository, ShipmentUtil shipmentUtil) {
        this.shipmentOrderRepository = shipmentOrderRepository;
        this.shipmentUtil = shipmentUtil;
    }
    @Transactional
    public String createShipmentOrderRequest(ShipmentOrderRequestData shipmentOrderRequestData) throws ShipmentOrderException {
        if (shipmentUtil.isOrderValid(shipmentOrderRequestData)) {
            try {
                shipmentOrderRequestData.setOrderId(ShipmentUtil.generateUniqueOrderId());
                Optional<ShipmentOrderRequestData> existingOrder = shipmentOrderRepository.findById(shipmentOrderRequestData.getOrderId());

                if (existingOrder.isPresent()) {
                    throw new ShipmentOrderException("Order with ID " + shipmentOrderRequestData.getOrderId() + " already exists.");
                }
                ShipmentOrderRequestData savedRespone = shipmentOrderRepository.save(shipmentOrderRequestData);
                logger.info("Order Saved, Order Id: {}", savedRespone.getOrderId());
                return "Shipment order " + savedRespone.getOrderId() + " created successfully!";
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new ShipmentOrderException("Optimistic lock failure, please retry the operation.");
            } catch (Exception e) {
                throw new ShipmentOrderException("An error occurred while processing the shipment order: " + e.getMessage());
            }
        } else {
            throw new ShipmentOrderException("Invalid shipment order data request");
        }
    }

    public String getOrderStatus(Long orderId) {
        try {
            Optional<ShipmentOrderRequestData> orderResponse = shipmentOrderRepository.findById(orderId);
            if (orderResponse.isPresent()) {
                return orderResponse.get().getOrderStatus();
            }
        } catch (Exception ex) {
            logger.info("Exception while order tracking: {}", ex.getMessage());
        }
        throw new ShipmentOrderException("Invalid shipment order data request");
    }

}
