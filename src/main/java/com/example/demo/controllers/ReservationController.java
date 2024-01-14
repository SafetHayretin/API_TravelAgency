package com.example.demo.controllers;

import com.example.demo.abstractDTO.ReservationDTO;
import com.example.demo.holidayDTOs.CreateHolidayDTO;
import com.example.demo.holidayDTOs.ResponseHolidayDTO;
import com.example.demo.locationDTOs.ResponseLocationDTO;
import com.example.demo.models.Holiday;
import com.example.demo.models.Location;
import com.example.demo.models.Reservation;
import com.example.demo.repositories.HolidayRepository;
import com.example.demo.repositories.LocationRepository;
import com.example.demo.repositories.ReservationRepository;
import com.example.demo.reservationDTOs.CreateReservationDTO;
import com.example.demo.reservationDTOs.ResponseReservationDTO;
import com.example.demo.reservationDTOs.UpdateReservationDTO;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayController holidayController;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseReservationDTO> createReservation(@RequestBody CreateReservationDTO createReservationDTO) {
        try {
            Reservation reservation = convertToEntity(createReservationDTO);
            reservationRepository.save(reservation);
            ResponseReservationDTO responseReservationDTO = convertToDTO(reservation);

            return new ResponseEntity<>(responseReservationDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseReservationDTO convertToDTO(Reservation reservation) {
        ResponseReservationDTO responseReservationDTO = new ResponseReservationDTO();
        responseReservationDTO.setId(reservation.getId());
        Long id = reservation.getHolidayId();
        Holiday holiday = holidayRepository.getReferenceById(id);
        ResponseHolidayDTO holidayDto = holidayController.convertToDTO(holiday);
        responseReservationDTO.setHoliday(holidayDto);
        responseReservationDTO.setContactName(reservation.getContactName());
        responseReservationDTO.setPhoneNumber(reservation.getPhoneNumber());

        return responseReservationDTO;
    }

    private Reservation convertToEntity(CreateReservationDTO reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setContactName(reservationDto.getContactName());
        reservation.setHolidayId(reservationDto.getHoliday());
        reservation.setPhoneNumber(reservationDto.getPhoneNumber());

        return reservation;
    }

    private Reservation convertToEntity(UpdateReservationDTO reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setContactName(reservationDto.getContactName());
        reservation.setHolidayId(reservationDto.getHoliday());
        reservation.setPhoneNumber(reservationDto.getPhoneNumber());

        return reservation;
    }

    @DeleteMapping("/{reservationId}")
    public boolean deleteReservation(@PathVariable long reservationId) {
        try {
            reservationRepository.deleteById(reservationId);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseReservationDTO>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();

            return ResponseEntity.ok(convertAllToDTO(reservations));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<ResponseReservationDTO> convertAllToDTO(List<Reservation> reservations) {
        List<ResponseReservationDTO> reservationDTOS = new ArrayList<>();

        for (Reservation reservation : reservations) {
            reservationDTOS.add(convertToDTO(reservation));
        }

        return reservationDTOS;
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseReservationDTO> getReservationById(@PathVariable long reservationId) {
        try {
            Reservation reservation = reservationRepository.getReferenceById(reservationId);

            return ResponseEntity.ok(convertToDTO(reservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<ResponseReservationDTO> updateReservation(@RequestBody UpdateReservationDTO updatedReservation) {
        try {
            Reservation reservation = convertToEntity(updatedReservation);
            reservation.setId(updatedReservation.getId());
            reservationRepository.save(reservation);

            return ResponseEntity.ok(convertToDTO(reservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
