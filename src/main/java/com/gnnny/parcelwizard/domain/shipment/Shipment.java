package com.gnnny.parcelwizard.domain.shipment;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment {

    private ShipmentId shipmentId;
    private String trackingNo;
    private CourierCompany courierCompany;
    private Recipient recipient;
    private Sender sender;
    private List<ShipmentProgress> shipmentProgresses = new ArrayList<>();

    @Builder
    public Shipment(String trackingNo, CourierCompany courierCompany, Recipient recipient,
        Sender sender, List<ShipmentProgress> shipmentProgresses) {
        this.trackingNo = trackingNo;
        this.courierCompany = courierCompany;
        this.recipient = recipient;
        this.sender = sender;
        this.shipmentProgresses = shipmentProgresses;
    }

    @Getter
    public static class ShipmentId {
        private Long id;
    }
}
