package com.gnnny.parcelwizard.domain.shipmenttracking.service;

import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentTrackingService {

    private final ShipmentTracker shipmentTracker;

    public ShipmentTracking getTrackingDetail(CourierCompany courierCompany, String trackingNo) {
        log.info("[택배조회] {}, {}", courierCompany.getValue(), trackingNo);
        ShipmentTrackingStrategy deliveryStrategy = shipmentTracker.find(
            courierCompany);

        return deliveryStrategy.tracking(trackingNo);
    }
}
