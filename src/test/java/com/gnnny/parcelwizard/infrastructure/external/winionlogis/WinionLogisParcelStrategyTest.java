package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class WinionLogisParcelStrategyTest {

    @Mock
    private WinionLogisClient winionLogisClient;

    @InjectMocks
    private WinionLogisParcelStrategy winionLogisParcelStrategy;

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
        var deliveryInfo = winionLogisParcelStrategy.tracking(trackingNo);

        //then
        assertAll(
            () -> assertThat(deliveryInfo.getTrackingNo()).isEqualTo(trackingNo),
            () -> assertThat(deliveryInfo.getDeliveryCompany()).isEqualTo(DeliveryCompany.WINION_LOGIS),
            () -> assertThat(deliveryInfo.getDeliveryProgresses()).hasSize(7),
            () -> assertThat(deliveryInfo.getRecipient().getAddress()).contains("경기도 용인")
        );
    }

    @DisplayName("존재하지 않은 운송장 조회시 exception이 발생한다.")
    @Test
    void notSupportedTrackingInfo() throws IOException {
        //given
        var winionLogisApiResponse = objectMapper.readValue(
            new ClassPathResource(RESPONSE_FILE_PATH).getFile(),
            WinionLogisApiResponse.class);

        var trackingNo = "1234567890";

        given(winionLogisClient.getDeliveryProgressInfo(any())).willReturn(winionLogisApiResponse);

        //when
        var deliveryInfo = winionLogisParcelStrategy.tracking(trackingNo);

        //then
        assertAll(
            () -> assertThat(deliveryInfo.getTrackingNo()).isEqualTo(trackingNo),
            () -> assertThat(deliveryInfo.getDeliveryCompany()).isEqualTo(DeliveryCompany.WINION_LOGIS),
            () -> assertThat(deliveryInfo.getDeliveryProgresses()).hasSize(7),
            () -> assertThat(deliveryInfo.getRecipient().getAddress()).contains("경기도 용인")
        );
    }

    @Test
    void getParcelCompanyName() {
        //given
        var deliveryCompanyName= DeliveryCompany.WINION_LOGIS;

        //when
        var parcelCompanyName = winionLogisParcelStrategy.getParcelCompanyName();

        //then
        assertThat(parcelCompanyName.name()).isEqualTo(deliveryCompanyName.name());
    }
}
