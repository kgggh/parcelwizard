package com.gnnny.parcelwizard.domain.delivery.service;

import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.Delivery;

public interface ParcelDeliveryStrategy {

    Delivery tracking(String trackingNo);

    DeliveryCompany getParcelCompanyName();
}
