package com.seg.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
}
