package com.gnnny.parcelwizard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    @Profile(value = {"dev", "test"})
    @Bean
    public Logger customLogger() {
        return new FeignCustomLogger();
    }

    @Slf4j
    public static class FeignCustomLogger extends Logger {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        protected void log(String configKey, String format, Object... args) {}

        @SneakyThrows
        @Override
        protected void logRequest(String configKey, Level logLevel, Request request) {
            String bodyData =
                request.body() != null ? mapper.writeValueAsString(request.body()) : "";

            log.info(String.format("[요청] %s %s %s", request.httpMethod(), request.url(),
                bodyData));

            super.logRequest(configKey, logLevel, request);
        }

        @SneakyThrows
        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel,
            Response response,
            long elapsedTime) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());

            log.info(String.format("[응답] %d %s", response.status(), new String(bodyData)));

            return response.toBuilder().body(bodyData).build();
        }

        @Override
        protected IOException logIOException(String configKey, Level logLevel, IOException ioe,
            long elapsedTime) {
            log.error("[요청실패] {}", ioe.getMessage(), ioe);

            return super.logIOException(configKey, logLevel, ioe, elapsedTime);
        }
    }
}

