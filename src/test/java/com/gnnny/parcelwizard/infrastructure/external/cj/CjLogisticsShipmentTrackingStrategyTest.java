package com.gnnny.parcelwizard.infrastructure.external.cj;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CjLogisticsShipmentTrackingStrategyTest {

    @Autowired
    private CjLogisticsDeliveryStrategy cjLogisticsDeliveryStrategy;

    public static WireMockServer wiremock =
        new WireMockServer(WireMockSpring.options().port(33333).usingFilesUnderClasspath("wiremock"));

    @BeforeAll
    static void setup() {
        wiremock.start();
    }

    @AfterEach
    void reset() {
        wiremock.resetAll();
    }

    @AfterAll
    static void clean() {
        wiremock.shutdown();
    }

    @DisplayName("배송조회[CJ대한통운] 데이터를 가져올수 있다.")
    @Test
    void tracking() {
        //given

        var trackingNo = "1234567890";

        //when
        var deliveryInfo = cjLogisticsDeliveryStrategy.tracking(trackingNo);

        //then
        assertAll(
            () -> assertThat(deliveryInfo.getTrackingNo()).isEqualTo(trackingNo),
            () -> assertThat(deliveryInfo.getCourierCompany()).isEqualTo(CourierCompany.CJ_LOGISTICS),
            () -> assertThat(deliveryInfo.getShipmentTrackingProgresses()).hasSize(5),
            () -> assertThat(deliveryInfo.getRecipient().getAddress()).contains("경기도 용인")
        );

    }
}
