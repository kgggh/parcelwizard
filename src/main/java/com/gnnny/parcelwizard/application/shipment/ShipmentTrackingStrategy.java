package com.gnnny.parcelwizard.application.shipment;

import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.domain.shipment.Shipment;

public interface ShipmentTrackingStrategy {

    Shipment tracking(String trackingNo);

    CourierCompany getCourierCompanyName();
}
