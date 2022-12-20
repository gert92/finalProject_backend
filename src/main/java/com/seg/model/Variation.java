package com.seg.model;

import com.seg.constants.MealPlans;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Can not be empty")
    private Date startDate;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private int numberOfNights;
    @NonNull
    @NotBlank(message = "Can not be empty")
    @Enumerated(EnumType.STRING)
    private MealPlans plan;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private Double price;
    @NotBlank(message = "Can not be empty")
    @NonNull
    private int freeSeats;




}
