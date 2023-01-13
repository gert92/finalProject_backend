package com.seg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString
public class Hotel implements Slugify {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @NotBlank(message = "Can not be empty")
    private String name;
    @NonNull
    @NotBlank(message = "Can not be empty")
    @Column(columnDefinition = "text")
    private String description;
    @NonNull
    private String tag;
    @NonNull
    private String novatoursKey;
    @NonNull
    @JsonIgnoreProperties(value = {"hotels"}, allowSetters = true)
    @ManyToOne
    @NotNull(message = "please provide a country for this hotel")
    private Country country;
    @NonNull
    @ManyToOne
    @NotNull(message = "please provide a city for this hotel")
    private City city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Variation> variations;

    private int counter =0;
    @NonNull
    @NotBlank(message = "please provide image URL")
    private String image;


}

