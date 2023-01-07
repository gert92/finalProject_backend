package com.seg.repository;

import com.seg.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {
    public List<Hotel> findByName(String name);

    public List<Hotel> findByTagContains (String tag);
}
