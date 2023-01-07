package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "Please provide your details")
    private Customer customer;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "variation_id", referencedColumnName = "id")
    @NotNull(message = "Please choose a package")
    private Variation packageVariation;

    @NonNull
    @NotNull(message = "Please choose a date")
    private Date departureDate;

    @NonNull
    @NotNull(message = "Please choose a date")
    private Date returnDate;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @NotNull(message = "Please choose a Hotel")
    private Hotel hotel;

    @NonNull
    @NotNull(message = "Please add number of adults")
    private int adults;

    @NonNull
    @NotNull(message = "Please add number of children")
    private int children;


}
