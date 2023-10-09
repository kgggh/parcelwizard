package com.gnnny.parcelwizard.application.customsclearance.impl;

import com.gnnny.parcelwizard.application.customsclearance.CustomsClearanceTrackingUseCase;
import com.gnnny.parcelwizard.application.customsclearance.model.CustomsClearanceDto;
import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearance;
import com.gnnny.parcelwizard.infrastructure.external.unipass.CustomsClearanceHttpAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CustomsClearanceTrackingService implements CustomsClearanceTrackingUseCase {

    private final CustomsClearanceHttpAdapter customsClearanceHttpAdapter;

    @Override
    public CustomsClearanceDto getTrackingDetail(Integer year, String houseBlNo) {
        if (year == null || !StringUtils.hasText(houseBlNo)) {
            throw new IllegalArgumentException("년도 혹은 선하증권번호를 확인바랍니다.");
        }

        CustomsClearance customsClearance =
            customsClearanceHttpAdapter.getCargoClearanceDetail(year, houseBlNo)
            .orElseThrow(() -> new IllegalStateException("통관정보가 조회되지 않습니다."));

        return new CustomsClearanceDto(customsClearance);
    }
}
