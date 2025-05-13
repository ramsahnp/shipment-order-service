package com.shipment.order.management.courier_shipment_api.utils;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShipmentUtil {
    public static boolean isOrderValid(ShipmentOrderRequestData shipmentOrderRequestData) {
        return shipmentOrderRequestData.getOrderTotal().compareTo(BigDecimal.ZERO) > 0;
    }
    public static Long generateUniqueOrderId() {
        long timestamp = System.currentTimeMillis() % 1_000_000L;
        int random = (int)(Math.random() * 9000) + 1000;
        return Long.parseLong(String.format("%06d%04d", timestamp, random));
    }
}
