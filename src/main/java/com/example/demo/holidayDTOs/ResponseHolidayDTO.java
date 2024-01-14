package com.example.demo.holidayDTOs;

import com.example.demo.abstractDTO.HolidayDTO;
import com.example.demo.locationDTOs.ResponseLocationDTO;

public class ResponseHolidayDTO extends HolidayDTO {
    private Long id;
    private ResponseLocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponseLocationDTO getLocation() {
        return this.location;
    }

    public void setLocation(ResponseLocationDTO location) {
        this.location = location;
    }
}
