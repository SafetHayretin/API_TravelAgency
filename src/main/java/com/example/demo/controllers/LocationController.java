package com.example.demo.controllers;

import com.example.demo.abstractDTO.LocationDTO;
import com.example.demo.locationDTOs.CreateLocationDTO;
import com.example.demo.locationDTOs.ResponseLocationDTO;
import com.example.demo.locationDTOs.UpdateLocationDTO;
import com.example.demo.models.Location;
import com.example.demo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseLocationDTO> createLocation(@RequestBody CreateLocationDTO createLocationDTO) {
        try {
            Location location = convertToEntity(createLocationDTO);
            locationRepository.save(location);
            ResponseLocationDTO responseLocationDTO = convertToDTO(location);

            return new ResponseEntity<>(responseLocationDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseLocationDTO convertToDTO(Location location) {
        ResponseLocationDTO responseLocationDTO = new ResponseLocationDTO();
        responseLocationDTO.setId(location.getId());
        responseLocationDTO.setCity(location.getCity());
        responseLocationDTO.setCountry(location.getCountry());
        responseLocationDTO.setNumber(location.getNumber());
        responseLocationDTO.setStreet(location.getStreet());
        responseLocationDTO.setImageUrl(location.getImageUrl());

        return responseLocationDTO;
    }

    private Location convertToEntity(LocationDTO locationDTO) {
        Location location = new Location();
        location.setCity(locationDTO.getCity());
        location.setCountry(locationDTO.getCountry());
        location.setNumber(locationDTO.getNumber());
        location.setStreet(locationDTO.getStreet());
        location.setImageUrl(locationDTO.getImageUrl());

        return location;
    }

    @DeleteMapping("/{locationId}")
    public boolean deleteLocation(@PathVariable long locationId) {
        try {
            locationRepository.deleteById(locationId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseLocationDTO>> getAllLocations() {
        try {
            List<Location> locations = locationRepository.findAll();

            return ResponseEntity.ok(convertAllToDTO(locations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<ResponseLocationDTO> convertAllToDTO(List<Location> locations) {
        List<ResponseLocationDTO> locationDTOS = new ArrayList<>();

        for (Location location : locations) {
            locationDTOS.add(convertToDTO(location));
        }

        return locationDTOS;
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<ResponseLocationDTO> getLocationById(@PathVariable long locationId) {
        try {
            Location locations = locationRepository.getReferenceById(locationId);

            return ResponseEntity.ok(convertToDTO(locations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ResponseLocationDTO> updateLocation(@RequestBody UpdateLocationDTO updatedLocation) {
        try {
            Location location = convertToEntity(updatedLocation);
            location.setId(updatedLocation.getId());
            locationRepository.save(location);

            return ResponseEntity.ok(convertToDTO(location));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
