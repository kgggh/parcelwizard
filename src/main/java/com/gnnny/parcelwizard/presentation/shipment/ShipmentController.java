package com.gnnny.parcelwizard.presentation.shipment;

import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingUseCase;
import com.gnnny.parcelwizard.application.shipment.model.ShipmentDto;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.presentation.shipment.resource.ShipmentTrackingResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentTrackingUseCase shipmentTrackingUseCase;

    @GetMapping("/api/v1/shipments/tracking")
    public ResponseEntity<ShipmentTrackingResource> get(String deliveryCompany, String trackingNo) {
        ShipmentDto shipmentDto = shipmentTrackingUseCase.getTrackingDetail(
            CourierCompany.valueOf(deliveryCompany), trackingNo);

        return ResponseEntity.ok(new ShipmentTrackingResource(shipmentDto));
    }
}
