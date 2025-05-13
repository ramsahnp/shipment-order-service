package com.shipment.order.management.courier_shipment_api.repository;

import com.shipment.order.management.courier_shipment_api.dto.ShipmentOrderRequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentOrderRepository extends JpaRepository<ShipmentOrderRequestData, Long> {

}
