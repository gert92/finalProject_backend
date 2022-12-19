package com.seg.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String name;
}
