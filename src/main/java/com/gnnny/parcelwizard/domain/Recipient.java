package com.gnnny.parcelwizard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipient {

    private String name;
    private String phoneNumber;
    private String address;
}
