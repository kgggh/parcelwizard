package com.gnnny.parcelwizard.infrastructure.external.cj;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${external-api.delivery.cj-logistics.base-url}", name = "cj-logistics-api")
public interface CjLogisticsClient {

    @PostMapping(path = "/web/rest/selectScanInfo.do")
    Map<String, Object> getDeliveryProgressInfo(@RequestParam("slipno") String trackingNo);

    @PostMapping(path = "/web/rest/selectWblNoInfoDt.do")
    Map<String, Object> getDeliveryDetail(@RequestParam("slipno") String trackingNo);

}