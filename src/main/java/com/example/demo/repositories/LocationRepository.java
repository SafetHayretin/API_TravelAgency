package com.example.demo.repositories;

import com.example.demo.models.Holiday;
import com.example.demo.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    public default List<Long> findByLocation(String cityOrCountry){
        List<Location> locations = findAll();
        List<Long> result = new ArrayList<>();
        for (Location location : locations) {
            if (location.getCountry().equals(cityOrCountry))
                result.add(location.getId());
            if (location.getCity().equals(cityOrCountry))
                result.add(location.getId());
        }

        return result;
    }
}
