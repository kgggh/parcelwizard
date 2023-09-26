package com.gnnny.parcelwizard.infrastructure.external.unipass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CargoClearanceProgressSearchRequestDto {

    // 인증키
    private String crkyCn;

    // 운송장번호 - House BL No
    private String hblNo;

    // 입항년도(MBL 또는 HBL 입력시 필수 입력) ex) 2023
    private Integer blYy;
}
