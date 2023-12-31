package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.gnnny.parcelwizard.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${external-api.delivery.cj-logistics.base-url}", name = "cj-logistics-api", configuration = FeignConfig.class)
public interface CjLogisticsClient {

    @PostMapping(path = "/web/rest/selectScanInfo.do")
    CjApiResponse getDeliveryProgressInfo(@RequestParam(name = "slipno") String trackingNo);

    @PostMapping(path = "/web/rest/selectWblNoInfoDt.do")
    CjApiResponse getDeliveryDetail(@RequestParam(name = "slipno") String trackingNo);
}
