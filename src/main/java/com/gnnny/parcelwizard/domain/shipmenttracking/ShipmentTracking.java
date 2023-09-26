package com.gnnny.parcelwizard.domain.shipmenttracking;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentTracking {

    private DeliveryId deliveryId;
    private String trackingNo;
    private CourierCompany courierCompany;
    private Recipient recipient;
    private Sender sender;
    private List<ShipmentTrackingProgress> shipmentTrackingProgresses = new ArrayList<>();

    @Builder
    public ShipmentTracking(String trackingNo, CourierCompany courierCompany, Recipient recipient,
        Sender sender, List<ShipmentTrackingProgress> shipmentTrackingProgresses) {
        this.trackingNo = trackingNo;
        this.courierCompany = courierCompany;
        this.recipient = recipient;
        this.sender = sender;
        this.shipmentTrackingProgresses = shipmentTrackingProgresses;
    }

    public class DeliveryId {
        private Long deliveryId;
    }
}
