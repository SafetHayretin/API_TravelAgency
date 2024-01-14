package com.example.demo.reservationDTOs;

import com.example.demo.abstractDTO.ReservationDTO;
import com.example.demo.holidayDTOs.ResponseHolidayDTO;

public class ResponseReservationDTO extends ReservationDTO {
    private Long id;

    private ResponseHolidayDTO holiday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponseHolidayDTO getResponseHolidayDTO() {
        return holiday;
    }

    public void setResponseHolidayDTO(ResponseHolidayDTO responseHolidayDTO) {
        this.holiday = responseHolidayDTO;
    }
}
