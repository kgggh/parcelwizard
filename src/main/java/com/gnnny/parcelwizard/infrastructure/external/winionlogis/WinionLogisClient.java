package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gnnny.parcelwizard.config.FeignConfig;
import com.gnnny.parcelwizard.infrastructure.external.winionlogis.WinionLogisClient.WinionLogisFeignDecoder;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${external-api.delivery.winion-logis.base-url}", name = "winion-logis-api", configuration = {
    FeignConfig.class, WinionLogisFeignDecoder.class})
public interface WinionLogisClient {

    @PostMapping(path = "/GetSmartData")
    WinionLogisApiResponse getDeliveryProgressInfo(
        @RequestBody DeliveryProgressSearchRequest deliveryProgressSearchRequest);

    class WinionLogisFeignDecoder implements Decoder {

        @Override
        public Object decode(Response response, Type type) throws IOException {
            String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            return new ObjectMapper().readValue(bodyStr, javaType);
        }
    }
}
