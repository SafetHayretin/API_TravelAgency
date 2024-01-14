package com.example.demo.holidayDTOs;

import com.example.demo.abstractDTO.HolidayDTO;
import com.example.demo.controllers.LocationController;
import com.example.demo.models.Location;

public class CreateHolidayDTO extends HolidayDTO {
    private Long location;

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }
}
