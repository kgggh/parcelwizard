package com.gnnny.parcelwizard.application.shipment.impl;

import com.gnnny.parcelwizard.application.shipment.model.ShipmentDto;
import com.gnnny.parcelwizard.application.shipment.ShipmentTracker;
import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingUseCase;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.domain.shipment.Shipment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentTrackingService implements ShipmentTrackingUseCase {

    private final ShipmentTracker shipmentTracker;

    @Override
    public ShipmentDto getTrackingDetail(CourierCompany courierCompany, String trackingNo) {
        log.info("[택배조회] {}, {}", courierCompany.getValue(), trackingNo);
        ShipmentTrackingStrategy deliveryStrategy = shipmentTracker.find(courierCompany);

        Shipment shipment = deliveryStrategy.tracking(trackingNo);

        return new ShipmentDto(shipment);
    }
}
