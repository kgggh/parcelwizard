package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gnnny.parcelwizard.infrastructure.external.ThirdPartyApiResponse;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CjApiResponse implements ThirdPartyApiResponse {

    private String resultMessage;
    private Integer resultCode;
    private Map<String, Object> data;

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WblNoOutput {

        private String dlvEmpnoNm;
        private String rcvrNm;
        private String rltWblNo;
        private String wblNo;
        private String qty;
        private String basisSclsfCdNm;
        private String sndrNm;
        private String picHpNo;
        private String rcvrAddr;
        private String picBranNm;
        private String picEmpnoNm;
        private String dlvHpNo;
        private String itmlNm;
        private String acprRlpNm;
        private String dlvBranNm;
        private String picTel;
        private String rcvrTel;
        private String wblDcd;
        private String dlvTel;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ScanInfoOutput {

        private String empynm;
        private String wblNo;
        private String rtlnNo;
        private String scanHms;
        private String basisSclsfCdNm;
        private String scanDt;
        private String branNm;
        private String branCd;
        private String patnBranCd;
        private String telno;
        private String oposNm;
        private String empno;
        private String crgStDcd;
        private String callBackNum;
        private String workHmsCd;
        private String branTelno;
    }
}
