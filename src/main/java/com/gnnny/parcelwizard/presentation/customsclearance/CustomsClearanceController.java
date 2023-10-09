package com.gnnny.parcelwizard.presentation.customsclearance;

import com.gnnny.parcelwizard.application.customsclearance.CustomsClearanceTrackingUseCase;
import com.gnnny.parcelwizard.application.customsclearance.model.CustomsClearanceDto;
import com.gnnny.parcelwizard.presentation.customsclearance.resource.CustomsClearanceTrackingResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomsClearanceController {

    private final CustomsClearanceTrackingUseCase customsClearanceTrackingUseCase;

    @GetMapping("/api/v1/customs-clearances/tracking")
    public ResponseEntity<CustomsClearanceTrackingResource> get(Integer year, String houseBlNo) {
        CustomsClearanceDto customsClearanceDto =
            customsClearanceTrackingUseCase.getTrackingDetail(year, houseBlNo);

        return ResponseEntity.ok(new CustomsClearanceTrackingResource(customsClearanceDto));
    }
}
