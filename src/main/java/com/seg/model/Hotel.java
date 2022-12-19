package com.seg.model;

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
    private String tag;
    @NonNull
    @ManyToOne
    private Country country;
    @NonNull
    @ManyToOne
    private City city;
    // delete the following
    @NonNull
    @OneToMany
    private List<Variation> Packages;

}
