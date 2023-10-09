package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.gnnny.parcelwizard.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "${external-api.customs-clearance.uni-pass.base-url}", name = "unipass-api", configuration = FeignConfig.class)
public interface UniPassClient {

    @GetMapping(path = "/ext/rest/cargCsclPrgsInfoQry/retrieveCargCsclPrgsInfo", consumes = MediaType.APPLICATION_XML_VALUE)
    CustomsClearanceProgressResponse getCargoClearanceProgress(
        @SpringQueryMap CustomsClearanceProgressSearchRequest customsClearanceProgressSearchRequest);
}
