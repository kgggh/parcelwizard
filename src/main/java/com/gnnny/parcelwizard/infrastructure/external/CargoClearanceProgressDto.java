package com.gnnny.parcelwizard.infrastructure.external;

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

    @JacksonXmlProperty(localName = "cargCsclPrgsInfoDtlQryVo")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Progress> progresses;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Progress {

        // 장치장명
        private String shedNm;

        //처리일시
        private String prcsDttm;

        //신고번호
        private String dclrNo;

        //반출입일시
        private String rlbrDttm;

        //포장개수
        private Integer pckGcnt;

        //포장다누이
        private String pckUt;

        //중량
        private BigDecimal wght;

        //중량단위
        private String wghtUt;

        //처리구분
        private String cargTrcnRelaBsopTpcd;

        //장치장부호
        private String shedSgn;
    }
}
