package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "cargCsclPrgsInfoQryRtnVo")
public class CargoClearanceProgressDto {

    // 운송장 번호
    private String hblNo;

    //적재항명
    private String prnm;

    //총 중량
    private String ttwg;

    //중량 단위
    private String wghtUt;

    //포워더 명
    private String frwrEntsConm;

    @JacksonXmlProperty(localName = "cargCsclPrgsInfoDtlQryVo")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Progress> progresses;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Progress {

        // 장치장명
        private String shedNm;

        // 처리일시
        private String prcsDttm;

        // 신고번호
        private String dclrNo;

        // 반출입일시
        private String rlbrDttm;

        // 포장개수
        private Integer pckGcnt;

        // 포장단위
        private String pckUt;

        // 중량
        private BigDecimal wght;

        // 중량단위
        private String wghtUt;

        // 처리구분
        private String cargTrcnRelaBsopTpcd;

        // 사전안내내용
        private String bfhnGdncCn;

        // 장치장부호
        private String shedSgn;
    }
}
