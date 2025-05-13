package com.shipment.order.management.courier_shipment_api.exception;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import com.shipment.order.management.courier_shipment_api.utils.ShipmentUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class GlobalExceptionHandlerTest {
    @Test
    void testIsOrderValid_WithValidOrderTotal() {
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData();
        requestData.setOrderTotal(new BigDecimal("100.00"));

        boolean result = ShipmentUtil.isOrderValid(requestData);

        assertTrue(result, "Expected order to be valid for positive total");
    }

    @Test
    void testIsOrderValid_WithZeroOrderTotal() {
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData();
        requestData.setOrderTotal(BigDecimal.ZERO);

        boolean result = ShipmentUtil.isOrderValid(requestData);

        assertFalse(result, "Expected order to be invalid for zero total");
    }

    @Test
    void testIsOrderValid_WithNegativeOrderTotal() {
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData();
        requestData.setOrderTotal(new BigDecimal("-50.00"));

        boolean result = ShipmentUtil.isOrderValid(requestData);

        assertFalse(result, "Expected order to be invalid for negative total");
    }

    @Test
    void testIsOrderValid_WithNullOrderTotal() {
        ShipmentOrderRequestData requestData = new ShipmentOrderRequestData();
        requestData.setOrderTotal(null);

        assertThrows(NullPointerException.class, () -> ShipmentUtil.isOrderValid(requestData),
                "Expected NullPointerException when orderTotal is null");
    }

    @Test
    void testGenerateUniqueOrderId_IsAlwaysTenDigits() {
        Long orderId = ShipmentUtil.generateUniqueOrderId();

        assertNotNull(orderId, "Order ID should not be null");
        assertEquals(10, String.valueOf(orderId).length(), "Order ID should be exactly 10 digits");
    }

    @Test
    void testGenerateUniqueOrderId_Uniqueness() {
        Long id1 = ShipmentUtil.generateUniqueOrderId();
        Long id2 = ShipmentUtil.generateUniqueOrderId();

        assertNotEquals(id1, id2, "Generated order IDs should not be the same");
    }
}
