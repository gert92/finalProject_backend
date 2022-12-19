package com.seg.model;

import jakarta.persistence.*;
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
    @ManyToOne
    private User user;

    @NonNull
    @OneToOne
    private Variation aPackage;

    @NonNull
    private Date departureDate;

    @NonNull
    private Date returnDate;

    @NonNull
    @OneToOne
    private Hotel hotel;

    @NonNull
    private int numberOfAdults;

    @NonNull
    private int numberOfChildren;


}
