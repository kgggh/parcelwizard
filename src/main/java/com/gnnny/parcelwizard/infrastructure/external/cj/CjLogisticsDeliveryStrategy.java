package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.Delivery;
import com.gnnny.parcelwizard.domain.delivery.DeliveryProgress;
import com.gnnny.parcelwizard.domain.delivery.DeliveryStatus;
import com.gnnny.parcelwizard.domain.delivery.Recipient;
import com.gnnny.parcelwizard.domain.delivery.Sender;
import com.gnnny.parcelwizard.domain.delivery.service.ParcelDeliveryStrategy;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.ScanInfoOutput;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.WblNoOutput;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CjLogisticsDeliveryStrategy implements ParcelDeliveryStrategy {

    private final CjLogisticsClient cjLogisticsClient;
    private final ObjectMapper objectMapper;

    @Override
    public Delivery tracking(String trackingNo) {
        try {
            CjApiResponse deliveryDetail = cjLogisticsClient.getDeliveryDetail(trackingNo);
            WblNoOutput wblNoOutput = objectMapper.convertValue(
                deliveryDetail.getData().get("wblNoOutput"), WblNoOutput.class);

            CjApiResponse deliveryProgressInfo = cjLogisticsClient.getDeliveryProgressInfo(
                trackingNo);
            List<ScanInfoOutput> scanInfoOutput =
                objectMapper.convertValue(deliveryProgressInfo.getData().get("scanInfoOutput"),
                    new TypeReference<>() {
                    });

            return toDomain(wblNoOutput, scanInfoOutput);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Delivery toDomain(WblNoOutput wblNoOutput, List<ScanInfoOutput> scanInfoOutputs) {
        return Delivery.builder()
            .trackingNo(wblNoOutput.getWblNo())
            .deliveryCompany(DeliveryCompany.CJ_LOGISTICS)
            .recipient(
                new Recipient(wblNoOutput.getRcvrNm(), wblNoOutput.getRcvrTel(),
                    wblNoOutput.getRcvrAddr())
            )
            .sender(new Sender(wblNoOutput.getSndrNm(), "", ""))
            .deliveryProgresses(scanInfoOutputs.stream()
                .map(
                    scanInfoOutput -> DeliveryProgress.builder()
                        .location(scanInfoOutput.getBranNm())
                        .status(DeliveryStatus.matchedStatus(scanInfoOutput.getBasisSclsfCdNm()))
                        .detailStatus(scanInfoOutput.getBasisSclsfCdNm())
                        .processingDateTime(
                            DateUtil.parse(
                                String.format("%s %s", scanInfoOutput.getScanDt(), scanInfoOutput.getScanHms())))
                        .build()).toList()
            ).build();
    }

    @Override
    public DeliveryCompany getParcelCompanyName() {
        return DeliveryCompany.CJ_LOGISTICS;
    }

}
