package com.gnnny.parcelwizard.domain.shipmenttracking.service;

import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTracker {

    private Map<CourierCompany, ShipmentTrackingStrategy> deliveryStrategies;

    public ShipmentTracker(Set<ShipmentTrackingStrategy> parcelDeliveryStrategies) {
        initialize(parcelDeliveryStrategies);
    }

    public ShipmentTrackingStrategy find(CourierCompany courierCompany) {
        if (courierCompany == null) {
            throw new IllegalArgumentException("택배회사를 확인해주세요.");
        }

        return this.deliveryStrategies.get(courierCompany);
    }

    private void initialize(Set<ShipmentTrackingStrategy> parcelDeliveryStrategies) {
        this.deliveryStrategies = new EnumMap<>(CourierCompany.class);

        for (ShipmentTrackingStrategy shipmentTrackingStrategy : parcelDeliveryStrategies) {
            deliveryStrategies.put(shipmentTrackingStrategy.getCourierCompanyName(),
                shipmentTrackingStrategy);
        }
        System.out.println(deliveryStrategies);
    }
}
