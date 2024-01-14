package com.example.demo.reservationDTOs;

import com.example.demo.abstractDTO.ReservationDTO;

public class CreateReservationDTO extends ReservationDTO {
    private Long holiday;

    public Long getHoliday() {
        return holiday;
    }

    public void setHoliday(Long holiday) {
        this.holiday = holiday;
    }
}
