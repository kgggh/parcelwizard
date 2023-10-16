package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.gnnny.parcelwizard.infrastructure.external.krpost.KoreaPostApiResponse.DeliveryDetail;
import com.gnnny.parcelwizard.infrastructure.external.krpost.KoreaPostApiResponse.DeliveryProgress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class KoreaPostClient {

    @Value("${external-api.delivery.kr-post.base-url}")
    private String baseUrl;

    public KoreaPostApiResponse getDeliveryProgressInfo(String trackingNo) {
        String requestUrl = makeGetTrackingInfoQueryUrl(trackingNo);
        Document document = connect(requestUrl);

        DeliveryDetail deliveryDetail = extractDeliveryDetail(
            document.select("#print > .table_col > tbody > tr"));

        List<DeliveryProgress> deliveryProgresses = extractDeliveryProgresses(
            document.select("#processTable > tbody > tr"));

        return new KoreaPostApiResponse(deliveryDetail, deliveryProgresses);
    }

    private Document connect(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException("해당 URL에 대해 응답이 없습니다.");
        }
    }

    private String makeGetTrackingInfoQueryUrl(String trackingNo) {
        String uri = "/trace.RetrieveDomRigiTraceList.comm?displayHeader=N&sid1={trackingNo}";

        return UriComponentsBuilder
            .fromHttpUrl(baseUrl + uri)
            .buildAndExpand(trackingNo)
            .toUriString();
    }

    private DeliveryDetail extractDeliveryDetail(Elements elements) {
        String[] extractDeliveryDetailTexts = extractTexts(elements);


        if (extractDeliveryDetailTexts.length < 4) {
            throw new IllegalStateException("조회되지 않는 운송장 번호입니다.");
        }

        return new DeliveryDetail(
            extractDeliveryDetailTexts[0],
            extractDeliveryDetailTexts[1],
            extractDeliveryDetailTexts[3]
        );
    }

    private List<DeliveryProgress> extractDeliveryProgresses(Elements elements) {
        List<DeliveryProgress> deliveryProgresses = new ArrayList<>();
        for (Element element : elements) {
            Elements rows = element.select("td");
            String[] extractDeliveryProgressesTexts = extractTexts(rows);

            if (extractDeliveryProgressesTexts.length == 0) {
                continue;
            }

            if(extractDeliveryProgressesTexts.length > 4 && (extractDeliveryProgressesTexts[4].contains("배달"))) {
                    extractDeliveryProgressesTexts[3] = "배달준비";
            }

            deliveryProgresses.add(
                new DeliveryProgress(
                    extractDeliveryProgressesTexts[0],
                    extractDeliveryProgressesTexts[1],
                    extractDeliveryProgressesTexts[2],
                    extractDeliveryProgressesTexts[3]
                ));
        }
        return deliveryProgresses;
    }

    private String[] extractTexts(Elements elements) {
        return elements.html()
            .replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>|( )", "")
            .split("\n");
    }
}
