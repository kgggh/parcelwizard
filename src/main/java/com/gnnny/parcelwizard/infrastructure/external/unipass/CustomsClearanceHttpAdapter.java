package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearance;
import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearanceProgress;
import com.gnnny.parcelwizard.domain.customsclearance.Weight;
import com.gnnny.parcelwizard.domain.customsclearance.WeightUnit;
import com.gnnny.parcelwizard.infrastructure.external.unipass.UniPassApiServiceKeys.UniPassApiServiceName;
import com.gnnny.parcelwizard.shared.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomsClearanceHttpAdapter {

    private final UniPassClient uniPassClient;

    public Optional<CustomsClearance> getCargoClearanceDetail(int year, String houseBlNo) {
        CustomsClearanceProgressSearchRequest customsClearanceProgressSearchRequest =
            makeRequestParameter(year, houseBlNo);

        CustomsClearanceProgressResponse cargoClearanceProgress =
            uniPassClient.getCargoClearanceProgress(customsClearanceProgressSearchRequest);

        if(!cargoClearanceProgress.isSuccess()) {
            return Optional.empty();
        }

        return Optional.ofNullable(toDomain(cargoClearanceProgress));
    }

    private CustomsClearanceProgressSearchRequest makeRequestParameter(int year, String houseBlNo) {
        String apiKey =
                UniPassApiServiceKeys.getServiceKey(UniPassApiServiceName.CARGO_CUSTOMS_CLEARANCE_PROGRESS);

        return new CustomsClearanceProgressSearchRequest(apiKey, houseBlNo, year);
    }

    private CustomsClearance toDomain(CustomsClearanceProgressResponse customsClearanceProgressResponse) {
        CustomsClearanceProgressResponse.Summary summary = customsClearanceProgressResponse.getSummary();

        return CustomsClearance.builder()
            .customsClearanceId(null)
            .houseBlNo(summary.getHblNo())
            .itemName(summary.getPrnm())
            .weight(
                    new Weight(
                            Double.parseDouble(summary.getTtwg()),
                            WeightUnit.valueOf(summary.getWghtUt())
                    )
            )
            .customsClearanceProgresses(
                customsClearanceProgressResponse.getProgresses().stream()
                    .map(progresses -> CustomsClearanceProgress.builder()
                            .status(progresses.getCargTrcnRelaBsopTpcd())
                            .detailStatus(progresses.getBfhnGdncCn())
                            .processingDateTime(DateUtil.parse(progresses.getPrcsDttm(), "yyyyMMddHHmmss"))
                            .build())
                    .sorted(Comparator.comparing(CustomsClearanceProgress::getProcessingDateTime)).toList()
            )
            .build();
    }
}
