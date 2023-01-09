package com.seg.repository;

import com.seg.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    public List<Hotel> findByName(String name);

    public Optional<Hotel> findHotelByTag(String tag);
    public List<Hotel> findByTagContains (String tag);
    List<Hotel> findByNameContainingIgnoreCaseOrCountryNameContainingIgnoreCaseOrCityNameContainingIgnoreCase(String hotelName, String countryName, String cityName);

}
