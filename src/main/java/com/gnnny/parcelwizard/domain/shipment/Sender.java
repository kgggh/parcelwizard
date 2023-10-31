package com.gnnny.parcelwizard.domain.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Sender {

    private String name;
    private String phoneNumber;
    private String address;

    public Sender() {
        this.name = "";
        this.phoneNumber = "";
        this.address = "";
    }
}
