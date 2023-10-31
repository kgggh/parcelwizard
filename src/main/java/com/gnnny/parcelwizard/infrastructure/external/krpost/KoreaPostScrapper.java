package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.gnnny.parcelwizard.infrastructure.external.common.InvalidTrackingNumberException;
import com.gnnny.parcelwizard.infrastructure.external.common.NonExistentTrackingNumberException;
import com.gnnny.parcelwizard.infrastructure.external.common.ThirdPartyAPIConnectionException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class KoreaPostScrapper {

    @Value("${external-api.delivery.kr-post.base-url}")
    private String baseUrl;

    private static final int SIGNIFICANT_FIGURES = 13;
    private static final String GET_TRACKING_INFO_URI = "/trace.RetrieveDomRigiTraceList.comm?displayHeader=N&sid1={trackingNo}";

    public KoreaPostApiResponse getDeliveryProgressInfo(String trackingNo) {
        String requestUrl = makeGetTrackingInfoQueryUrl(trackingNo);
        Document document = connect(requestUrl);

        KoreaPostApiResponse.DeliveryDetail deliveryDetail = extractDeliveryDetail(
                document.select("#print > .table_col > tbody > tr"));

        List<KoreaPostApiResponse.DeliveryProgress> deliveryProgresses = extractDeliveryProgresses(
                document.select("#processTable > tbody > tr"));

        return new KoreaPostApiResponse(deliveryDetail, deliveryProgresses);
    }

    private Document connect(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ThirdPartyAPIConnectionException(e);
        }
    }

    private String makeGetTrackingInfoQueryUrl(String trackingNo) {
        return UriComponentsBuilder
                .fromHttpUrl(baseUrl + GET_TRACKING_INFO_URI)
                .buildAndExpand(trackingNo)
                .toUriString();
    }

    private KoreaPostApiResponse.DeliveryDetail extractDeliveryDetail(Elements elements) {
        String[] extractDeliveryDetailTexts = extractTexts(elements);

        if(extractDeliveryDetailTexts.length < 6) {
            throw new InvalidTrackingNumberException(SIGNIFICANT_FIGURES);
        }

        if (extractDeliveryDetailTexts[6].contains("처리중")) {
            throw new NonExistentTrackingNumberException(extractDeliveryDetailTexts[0]);
        }

        return new KoreaPostApiResponse.DeliveryDetail(
                extractDeliveryDetailTexts[0],
                extractDeliveryDetailTexts[1],
                extractDeliveryDetailTexts[3]
        );
    }

    private List<KoreaPostApiResponse.DeliveryProgress> extractDeliveryProgresses(Elements elements) {
        List<KoreaPostApiResponse.DeliveryProgress> deliveryProgresses = new ArrayList<>();
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
                    new KoreaPostApiResponse.DeliveryProgress(
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
