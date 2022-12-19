package com.seg.model;

import com.seg.constants.MealPlans;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private Date startDate;
    @NonNull
    private int numberOfNights;
    @NonNull
    private MealPlans plan;
    @NonNull
    private Double price;
    @NonNull
    private int freeSeats;




}
