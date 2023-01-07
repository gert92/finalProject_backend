package com.seg.model;

import com.seg.constants.MealPlans;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "variations")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @NotNull(message = "please choose a date")
    private Date startDate;
    @NonNull
    @NotNull(message = "please enter number of nights")
    private int numberOfNights;
    @NonNull
    @NotNull(message = "please choose meal plan type")
    @Enumerated(EnumType.STRING)
    private MealPlans plan;
    @ManyToOne
    @NonNull
    @NotNull(message = "can't be empty")
    private Hotel hotel;
    @NonNull
    @NotNull(message = "please enter price for this variation")
    private Double price;
    @NotNull(message = "please enter available number of seats")
    @NonNull
    private int freeSeats;




}
