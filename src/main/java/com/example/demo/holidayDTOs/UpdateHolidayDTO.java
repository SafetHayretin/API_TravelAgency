package com.example.demo.holidayDTOs;

import com.example.demo.abstractDTO.HolidayDTO;

public class UpdateHolidayDTO extends HolidayDTO {
    private Long id;
    private Long location;

    public Long getLocation() {
        return location;
    }

    public void setLocationId(Long location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
