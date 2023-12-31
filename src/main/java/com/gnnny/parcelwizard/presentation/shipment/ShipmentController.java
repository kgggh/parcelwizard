package com.gnnny.parcelwizard.presentation.shipment;

import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingUseCase;
import com.gnnny.parcelwizard.application.shipment.model.ShipmentDto;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.presentation.shipment.resource.ShipmentTrackingResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentTrackingUseCase shipmentTrackingUseCase;

    @GetMapping("/v1/shipments/tracking")
    public ResponseEntity<ShipmentTrackingResource> get(String courierCompany, String trackingNo) {
        ShipmentDto shipmentDto = shipmentTrackingUseCase.getTrackingDetail(
            CourierCompany.valueOf(courierCompany), trackingNo);

        return ResponseEntity.ok(new ShipmentTrackingResource(shipmentDto));
    }
}
