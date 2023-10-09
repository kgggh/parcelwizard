package com.gnnny.parcelwizard.application.customsclearance;

import com.gnnny.parcelwizard.application.customsclearance.model.CustomsClearanceDto;

public interface CustomsClearanceTrackingUseCase {

    CustomsClearanceDto getTrackingDetail(Integer year, String houseBlNo);
}
