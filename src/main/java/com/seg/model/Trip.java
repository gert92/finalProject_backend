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
    private User user;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @NotBlank(message = "Please choose a Hotel")
    private Hotel hotel;

    @NonNull
    @NotBlank(message = "Please add number of adults")
    private int adults;

    @NonNull
    @NotBlank(message = "Please add number of children")
    private int childs;


}
