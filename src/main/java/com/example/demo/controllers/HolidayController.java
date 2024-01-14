package com.example.demo.controllers;

import com.example.demo.holidayDTOs.CreateHolidayDTO;
import com.example.demo.abstractDTO.HolidayDTO;
import com.example.demo.holidayDTOs.ResponseHolidayDTO;
import com.example.demo.holidayDTOs.UpdateHolidayDTO;
import com.example.demo.locationDTOs.ResponseLocationDTO;
import com.example.demo.models.Holiday;
import com.example.demo.models.Location;
import com.example.demo.repositories.HolidayRepository;
import com.example.demo.repositories.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationController locationController;

    private static final Logger log = LoggerFactory.getLogger(HolidayController.class);

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseHolidayDTO> createHoliday(@RequestBody CreateHolidayDTO createHolidayDTO) {
        try {
            Holiday holiday = convertToEntity(createHolidayDTO);
            holidayRepository.save(holiday);
            ResponseHolidayDTO responseHolidayDTO = convertToDTO(holiday);

            return new ResponseEntity<>(responseHolidayDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseHolidayDTO convertToDTO(Holiday holiday) {
        ResponseHolidayDTO responseHolidayDTO = new ResponseHolidayDTO();
        responseHolidayDTO.setId(holiday.getId());
        responseHolidayDTO.setDuration(holiday.getDuration());
        responseHolidayDTO.setFreeSlots(holiday.getFreeSlots());
        responseHolidayDTO.setPrice(holiday.getPrice());
        Long id = holiday.getLocation();
        Location location = locationRepository.getReferenceById(id);
        ResponseLocationDTO locationDto = locationController.convertToDTO(location);
        responseHolidayDTO.setLocation(locationDto);
        responseHolidayDTO.setTitle(holiday.getTitle());
        responseHolidayDTO.setStartDate(holiday.getStartDate());

        return responseHolidayDTO;
    }

    private Holiday convertToEntity(CreateHolidayDTO holidayDto) {
        Holiday holiday = new Holiday();
        holiday.setDuration(holidayDto.getDuration());
        holiday.setFreeSlots(holidayDto.getFreeSlots());
        holiday.setPrice(holidayDto.getPrice());
        holiday.setLocation(holidayDto.getLocation());
        holiday.setTitle(holidayDto.getTitle());
        holiday.setStartDate(holidayDto.getStartDate());

        return holiday;
    }

    private Holiday convertToEntity(UpdateHolidayDTO holidayDto) {
        Holiday holiday = new Holiday();
        holiday.setDuration(holidayDto.getDuration());
        holiday.setFreeSlots(holidayDto.getFreeSlots());
        holiday.setPrice(holidayDto.getPrice());
        holiday.setLocation(holidayDto.getLocation());
        holiday.setTitle(holidayDto.getTitle());
        holiday.setStartDate(holidayDto.getStartDate());

        return holiday;
    }

    @DeleteMapping("/{holidayId}")
    public boolean deleteHoliday(@PathVariable long holidayId) {
        try {
            holidayRepository.deleteById(holidayId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseHolidayDTO>> getHolidayByLocation(@RequestParam(required = false) String location,
                                                                         @RequestParam(required = false) String duration,
                                                                         @RequestParam(required = false) String startDate) {
        String decodedLocation = (location != null) ? URLDecoder.decode(location, StandardCharsets.UTF_8) : null;
        try {
            List<Long> locationIds = (decodedLocation != null) ? locationRepository.findByLocation(decodedLocation) : null;
            List<Holiday> holiday = holidayRepository.findByDurationAndCountryAndStartDate(duration, locationIds, startDate);

            return ResponseEntity.ok(convertAllToDTO(holiday));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    List<ResponseHolidayDTO> convertAllToDTO(List<Holiday> holidays) {
        List<ResponseHolidayDTO> holidayDTOS = new ArrayList<>();

        for (Holiday holiday : holidays) {
            holidayDTOS.add(convertToDTO(holiday));
        }

        return holidayDTOS;
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<ResponseHolidayDTO> getHolidayById(@PathVariable long holidayId) {
        try {
            Holiday holiday = holidayRepository.getReferenceById(holidayId);

            return ResponseEntity.ok(convertToDTO(holiday));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ResponseHolidayDTO> updateHoliday(@RequestBody UpdateHolidayDTO updatedHoliday) {
        try {
            Holiday holiday = convertToEntity(updatedHoliday);
            holiday.setId(updatedHoliday.getId());
            holidayRepository.save(holiday);

            return ResponseEntity.ok(convertToDTO(holiday));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

