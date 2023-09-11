package com.gnnny.parcelwizard.infrastructure.external.unipass;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UniPassApiServiceKeys {

    private static Map<String, String> values = new HashMap<>();

    static {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            values = objectMapper.readValue(new File(
                new ClassPathResource("unipass_serivce_key.json").getFile().getPath()),
                new TypeReference<>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServiceKey(String serivceName) {
        if (Strings.isEmpty(serivceName)) {
            throw new IllegalArgumentException("사용하고자 하는 서비스를 입력해주세요.");
        }

        return values.get(serivceName);
    }
}
