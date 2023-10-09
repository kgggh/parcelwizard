package com.gnnny.parcelwizard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import java.io.IOException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FeignConfig {

    /**
     * Feign 로그 정책 설정 NONE : 로깅하지 않음(Default) BASIC : Request Method와 URL 그리고 Reponse 상태 코드와 실행 시간을
     * 로깅합니다. HEADER : Request, Response Header 정보와 함께 BASIC 정보를 로깅합니다. FULL : Request와 Response의
     * Header, Body 그리고 메타데이터를 로깅합니다.
     */
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

