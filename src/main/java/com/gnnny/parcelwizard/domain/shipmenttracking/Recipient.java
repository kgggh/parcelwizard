package com.gnnny.parcelwizard.domain.shipmenttracking;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Recipient {

    private String name;
    private String phoneNumber;
    private String address;
}
