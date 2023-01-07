package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Hotel> hotels;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String description;

    private int counter=0;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String image;

}
