package com.seg.model;

import com.seg.constants.Cities;
import com.seg.constants.Countries;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "hotels")
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String name;
    @NonNull
    private Countries country;
    @NonNull
    private Cities city;
    @NonNull
    private String address;
    @NonNull
    @OneToMany
    private List<Package> Packages;

}
