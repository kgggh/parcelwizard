package com.gnnny.parcelwizard.infrastructure.external.unipass;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${external-api.customs-clearance.uni-pass.base-url}", name = "unipass-api")
public interface UniPassClient {

    @GetMapping(path = "/ext/rest/cargCsclPrgsInfoQry/retrieveCargCsclPrgsInfo")
    CargoClearanceProgressDto getCargoClearanceProgress(
        @SpringQueryMap CargoClearanceProgressSearchRequestDto cargoClearanceProgressSearchRequestDto);
}
