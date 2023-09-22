package com.user.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Embedded
    private Name name;

    @Min(value = 18,message = "User must be at least 18+ years")
    @Max(value = 120,message = "Are you kidding me!")
    private int age;

    @NotBlank(message = "email cannot be null or empty")
    @Email(message = "Enter a valid email")
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Photo> photo;
}
