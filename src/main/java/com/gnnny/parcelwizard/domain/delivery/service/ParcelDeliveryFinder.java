package com.gnnny.parcelwizard.domain.delivery.service;

import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ParcelDeliveryFinder {

    private Map<DeliveryCompany, ParcelDeliveryStrategy> deliveryStrategies;

    public ParcelDeliveryFinder(Set<ParcelDeliveryStrategy> parcelDeliveryStrategies) {
        initialize(parcelDeliveryStrategies);
    }

    public ParcelDeliveryStrategy find(DeliveryCompany deliveryCompany) {
        if (deliveryCompany == null) {
            throw new IllegalArgumentException("택배회사를 확인해주세요.");
        }

        return this.deliveryStrategies.get(deliveryCompany);
    }

    private void initialize(Set<ParcelDeliveryStrategy> parcelDeliveryStrategies) {
        this.deliveryStrategies = new EnumMap<>(DeliveryCompany.class);

        for (ParcelDeliveryStrategy parcelDeliveryStrategy : parcelDeliveryStrategies) {
            deliveryStrategies.put(parcelDeliveryStrategy.getParcelCompanyName(),
                parcelDeliveryStrategy);
        }
        System.out.println(deliveryStrategies);
    }
}
