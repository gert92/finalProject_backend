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
@Table(name = "packages")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @NotNull(message = "please choose a date")
    private Date startDate;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private int numberOfNights;
    @NonNull
    @NotBlank(message = "Can not be empty")
    @Enumerated(EnumType.STRING)
    private MealPlans plan;
    @ManyToOne
    @NonNull
    @NotNull(message = "can't be empty")
    private Hotel hotel;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private Double price;
    @NotBlank(message = "Can not be empty")
    @NonNull
    private int freeSeats;




}
