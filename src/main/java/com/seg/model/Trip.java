package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "Please provide your details")
    private Customer customer;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Please choose a package")
    private Variation packageVariation;

    @NonNull
    @NotNull(message = "Please choose a date")
    private LocalDate departureDate;

    @NonNull
    @NotNull(message = "Please choose a date")
    private Date returnDate;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Please choose a Hotel")
    private Hotel hotel;

    @NonNull
    @NotNull(message = "Please add number of adults")
    private int adults;

    @NonNull
    @NotNull(message = "Please add number of children")
    private int children;


}
