package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class WinionLogisShipmentStrategyTest {

    @Mock
    private WinionLogisClient winionLogisClient;

    @InjectMocks
    private WinionLogisDeliveryStrategy winionLogisDeliveryStrategy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String RESPONSE_FILE_PATH = "sample/api_winion_logis_response.json";

    @DisplayName("배송조회[위니온로지스] 데이터를 가져올수 있다.")
    @Test
    void tracking() throws IOException {
        //given
        var winionLogisApiResponse = objectMapper.readValue(
            new ClassPathResource(RESPONSE_FILE_PATH).getFile(),
            WinionLogisApiResponse.class);

        var trackingNo = "1234567890";

        given(winionLogisClient.getDeliveryProgressInfo(any())).willReturn(winionLogisApiResponse);

        //when
        var deliveryInfo = winionLogisDeliveryStrategy.tracking(trackingNo);

        //then
        assertAll(
            () -> assertThat(deliveryInfo.getTrackingNo()).isEqualTo(trackingNo),
            () -> assertThat(deliveryInfo.getCourierCompany()).isEqualTo(CourierCompany.WINION_LOGIS),
            () -> assertThat(deliveryInfo.getShipmentProgresses()).hasSize(7),
            () -> assertThat(deliveryInfo.getRecipient().getAddress()).contains("경기도 용인")
        );
    }

    @DisplayName("존재하지 않은 운송장 조회시 exception이 발생한다.")
    @Test
    void notSupportedTrackingInfo() {
        //given
        var trackingNo = "1234567890";
        given(winionLogisClient.getDeliveryProgressInfo(any())).willReturn(null);

        //when
        //then
        assertThatThrownBy(() -> winionLogisDeliveryStrategy.tracking(trackingNo))
            .isInstanceOfAny(NullPointerException.class);

    }

    @Test
    void getCourierCompanyName() {
        //given
        var deliveryCompanyName= CourierCompany.WINION_LOGIS;

        //when
        var courierCompanyName = winionLogisDeliveryStrategy.getCourierCompanyName();

        //then
        assertThat(courierCompanyName.name()).isEqualTo(deliveryCompanyName.name());
    }
}
