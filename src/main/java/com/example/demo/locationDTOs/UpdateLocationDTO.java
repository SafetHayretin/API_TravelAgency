package com.example.demo.locationDTOs;

import com.example.demo.abstractDTO.LocationDTO;

public class UpdateLocationDTO extends LocationDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
