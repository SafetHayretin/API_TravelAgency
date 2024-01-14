package com.example.demo.repositories;

import com.example.demo.models.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    public default List<Holiday> findByLocation(List<Long> location) {
        List<Holiday> holidays = findAll();
        List<Holiday> result = new ArrayList<>();
        for (Holiday holiday : holidays) {
            if (location.contains(holiday.getLocation()))
                result.add(holiday);
        }

        return result;
    }

    @Query("SELECT e FROM Holiday e WHERE (:duration is null or e.duration = :duration) " +
            "AND (:locations is null or e.locationId IN :locations) " +
            "AND (:startDate is null or e.startDate = :startDate)")
    List<Holiday> findByDurationAndCountryAndStartDate(@Param("duration") String duration,
                                                       @Param("locations") List<Long> locations,
                                                       @Param("startDate") String startDate);
}
