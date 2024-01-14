package com.example.demo.abstractDTO;

import com.example.demo.models.Holiday;

public abstract class ReservationDTO {
    private String phoneNumber;
    private String contactName;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
