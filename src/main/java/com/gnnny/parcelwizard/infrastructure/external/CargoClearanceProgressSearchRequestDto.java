package com.gnnny.parcelwizard.infrastructure.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CargoClearanceProgressSearchRequestDto {

    // 화물관리번호
    private String crkyCn;

    // 화물관리번호
    private String cargMtNo;

    // Master BL No
    private Long mblNo;

    // House BL No
    private Long hblNo;

    //입항년도(MBL 또는 HBL 입력시 필수 입력)
    private Integer blYy;
}
