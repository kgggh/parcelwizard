package com.gnnny.parcelwizard.domain.delivery.service;


import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParcelDeliveryTrackingService {

    private final ParcelDeliveryFinder parcelDeliveryFinder;

    public Delivery getTrackingDetail(DeliveryCompany deliveryCompany, String trackingNo) {
        log.info("[택배조회] {}, {}", deliveryCompany.getValue(), trackingNo);
        ParcelDeliveryStrategy deliveryStrategy = parcelDeliveryFinder.find(
            deliveryCompany);

        return deliveryStrategy.tracking(trackingNo);
    }
}
