package com.seg.repository;

import com.seg.model.Variation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VariationRepository extends JpaRepository<Variation,Long> {

    public List<Variation> findByPriceLessThan(double price);

    public List<Variation> findByStartDateIsAfter(Date startDate);


}
