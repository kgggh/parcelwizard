package com.gnnny.parcelwizard.domain.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Recipient {

    private String name;
    private String phoneNumber;
    private String address;
}
