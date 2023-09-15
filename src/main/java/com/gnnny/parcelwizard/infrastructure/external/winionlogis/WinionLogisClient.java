package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${external-api.delivery.winion-logis.base-url}", name = "winion-logis-api", configuration = FeignConfig.class)
public interface WinionLogisClient {

    @PostMapping(path = "/GetSmartData")
    WinionLogisApiResponse getDeliveryProgressInfo(
        @RequestBody DeliveryProgressSearchRequestDto deliveryProgressSearchRequestDto);
}
