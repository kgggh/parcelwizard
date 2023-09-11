package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.infrastructure.external.config.FeignConfig;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${external-api.delivery.winion-logis.base-url}", name = "winion-logis-api", configuration = FeignConfig.class)
public interface WinionLogisClient {

    @PostMapping(path = "/GetSmartData")
    Map<String, Object> getDeliveryProgressInfo(
        @RequestBody DeliveryProgressSearchRequestDto deliveryProgressSearchRequestDto);
}
