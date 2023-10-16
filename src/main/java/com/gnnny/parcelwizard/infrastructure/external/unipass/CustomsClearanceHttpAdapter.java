package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearance;
import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearanceProgress;
import com.gnnny.parcelwizard.domain.customsclearance.Weight;
import com.gnnny.parcelwizard.domain.customsclearance.WeightUnit;
import com.gnnny.parcelwizard.infrastructure.external.unipass.UniPassApiServiceKeys.UniPassApiServiceName;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.Comparator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private CustomsClearance toDomain(
        CustomsClearanceProgressResponse customsClearanceProgressResponse) {
        return CustomsClearance.builder()
            .customsClearanceId(null)
            .houseBlNo(customsClearanceProgressResponse.getSummary().getHblNo())
            .itemName(customsClearanceProgressResponse.getSummary().getPrnm())
            .weight(new Weight(
                Double.parseDouble(customsClearanceProgressResponse.getSummary().getTtwg()),
                WeightUnit.valueOf(customsClearanceProgressResponse.getSummary().getWghtUt()))
            )
            .customsClearanceProgresses(
                customsClearanceProgressResponse.getProgresses().stream()
                    .map(i -> CustomsClearanceProgress.builder()
                            .status(i.getCargTrcnRelaBsopTpcd())
                            .detailStatus(i.getBfhnGdncCn())
                            .processingDateTime(DateUtil.parse(i.getPrcsDttm(), "yyyyMMddHHmmss"))
                            .build())
                    .sorted(Comparator.comparing(CustomsClearanceProgress::getProcessingDateTime))
                    .toList())
            .build();
    }

    private CustomsClearanceProgressSearchRequest makeRequestParameter(int year, String houseBlNo) {
        String apiKey =
            UniPassApiServiceKeys.getServiceKey(UniPassApiServiceName.CARGO_CUSTOMS_CLEARANCE_PROGRESS);

        return new CustomsClearanceProgressSearchRequest(apiKey, houseBlNo, year);
    }
}
