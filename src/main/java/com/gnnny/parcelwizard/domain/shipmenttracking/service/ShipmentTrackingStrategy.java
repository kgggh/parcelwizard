package com.gnnny.parcelwizard.domain.shipmenttracking.service;

import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;

public interface ShipmentTrackingStrategy {

    ShipmentTracking tracking(String trackingNo);

    CourierCompany getCourierCompanyName();
}
