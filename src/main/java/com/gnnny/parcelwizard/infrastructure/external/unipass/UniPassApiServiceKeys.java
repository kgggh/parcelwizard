package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UniPassApiServiceKeys {

    private static Map<String, String> values = new HashMap<>();

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream inputStream = null;

        try {
            inputStream = new ClassPathResource("key/unipass_serivce_key.json").getInputStream();
            File keyFile =File.createTempFile("unipass_serivce_key",".json");

            FileUtils.copyInputStreamToFile(inputStream, keyFile);

            values = objectMapper.readValue(keyFile,
                new TypeReference<>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static String getServiceKey(UniPassApiServiceName uniPassApiServiceName) {
        if (uniPassApiServiceName == null) {
            throw new IllegalArgumentException("사용하고자 하는 서비스를 입력해주세요.");
        }

        return values.get(uniPassApiServiceName.getServiceId());
    }

    @Getter
    enum UniPassApiServiceName {
        CARGO_CUSTOMS_CLEARANCE_PROGRESS("API001", "화물 통관 진행정보 조회");

        private final String serviceId;
        private final String description;

        UniPassApiServiceName(String serviceId, String description) {
            this.serviceId = serviceId;
            this.description = description;
        }
    }
}
