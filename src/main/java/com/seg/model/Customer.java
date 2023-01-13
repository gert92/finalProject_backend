package com.seg.model;

import com.seg.constants.PersonType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotBlank(message = "Name can't be empty")
    private String firstName;

    @NonNull
    @NotBlank(message = "Last name can't be empty")
    private String lastName;

    @NonNull
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email is not correct")
    private String email;

    @NonNull
    @Enumerated(EnumType.STRING)
    @NotNull(message = "please provide person type ")
    private PersonType personType;

    @NonNull
    @NotBlank(message = "Passport number can't be empty")
    private String passportNumber;
}
