package com.gnnny.parcelwizard.application.shipment;

import com.gnnny.parcelwizard.application.shipment.model.ShipmentDto;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;

public interface ShipmentTrackingUseCase {

    ShipmentDto getTrackingDetail(CourierCompany courierCompany, String trackingNo);
}
