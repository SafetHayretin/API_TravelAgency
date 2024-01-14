package com.example.demo.reservationDTOs;

import com.example.demo.abstractDTO.ReservationDTO;

public class UpdateReservationDTO extends ReservationDTO {
    private Long id;

    private Long holiday;

    public Long getHoliday() {
        return holiday;
    }

    public void setHoliday(Long holiday) {
        this.holiday = holiday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
