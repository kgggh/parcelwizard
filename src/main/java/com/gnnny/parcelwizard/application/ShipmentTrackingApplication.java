package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import com.gnnny.parcelwizard.domain.shipmenttracking.service.ShipmentTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentTrackingApplication {

    private final ShipmentTrackingService shipmentTrackingService;
    
    public ShipmentTrackingResult getParcelDeliveryDetail(String parcelCompany, String trackingNo) {
        ShipmentTracking shipmentTracking = shipmentTrackingService.getTrackingDetail(
            CourierCompany.valueOf(parcelCompany), trackingNo);

        return new ShipmentTrackingResult(shipmentTracking);
    }
}
