package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Line can't be empty")
    private Customer customer;

    @NonNull
    //relationship type
    @OneToOne
    @JoinColumn(name = "variation_id", referencedColumnName = "id")
    @NotBlank(message = "Line can't be empty")
    private Variation packageVariation;

    @NonNull
    @NotBlank(message = "Please choose a date")
    private Date departureDate;

    @NonNull
    @NotBlank(message = "Please choose a date")
    private Date returnDate;

    @NonNull
    //the cascade type.All doesn't seem right here as if we delete a trip the hotel will be deleted too
    @OneToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @NotBlank(message = "Please choose a Hotel")
    private Hotel hotel;

    @NonNull
    @NotBlank(message = "Please add number of adults")
    private int adults;

    @NonNull
    @NotBlank(message = "Please add number of children")
    private int child;


}
