package com.gnnny.parcelwizard.application.shipment;

import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ShipmentTracker {

    private Map<CourierCompany, ShipmentTrackingStrategy> shipmentStrategies;

    public ShipmentTracker(Set<ShipmentTrackingStrategy> parcelDeliveryStrategies) {
        initialize(parcelDeliveryStrategies);
    }

    public ShipmentTrackingStrategy find(CourierCompany courierCompany) {
        if (courierCompany == null) {
            throw new IllegalArgumentException("택배회사를 확인해주세요.");
        }

        return this.shipmentStrategies.get(courierCompany);
    }

    private void initialize(Set<ShipmentTrackingStrategy> parcelDeliveryStrategies) {
        this.shipmentStrategies = new EnumMap<>(CourierCompany.class);

        for (ShipmentTrackingStrategy shipmentTrackingStrategy : parcelDeliveryStrategies) {
            shipmentStrategies.put(shipmentTrackingStrategy.getCourierCompanyName(),
                shipmentTrackingStrategy);
        }
    }
}
