package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Can not be empty")
    private String name;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String tag;
    @NonNull
    @ManyToOne
    @NotNull
    private Country country;
    @NonNull
    @ManyToOne
    @NotNull
    private City city;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Variation> variations;

    private int counter =0;
    @NonNull
    @NotBlank(message = "please provide image URL")
    private String image;
    //this is changed

}
