package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.Delivery;
import com.gnnny.parcelwizard.domain.delivery.service.ParcelDeliveryTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryApplication {

    private final ParcelDeliveryTrackingService parcelDeliveryTrackingService;
    
    public ParcelDeliveryResult getParcelDeliveryDetail(String parcelCompany, String trackingNo) {
        Delivery delivery = parcelDeliveryTrackingService.getTrackingDetail(
            DeliveryCompany.valueOf(parcelCompany), trackingNo);

        return new ParcelDeliveryResult(delivery);
    }
}
