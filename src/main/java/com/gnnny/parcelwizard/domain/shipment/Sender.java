package com.gnnny.parcelwizard.domain.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Sender {

    private String name;
    private String phoneNumber;
    private String address;
}