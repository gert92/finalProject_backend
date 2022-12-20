package com.seg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Can not be empty")
    @ManyToOne
    private Country country;
    @NonNull
    @NotBlank(message = "Can not be empty")
    @ManyToOne
    private City city;
    // delete the following
    @NonNull
    @NotBlank(message = "Can not be empty")
    @OneToMany
    private List<Variation> variations;

}
