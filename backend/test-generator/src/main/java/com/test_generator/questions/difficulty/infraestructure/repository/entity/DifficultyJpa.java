package com.test_generator.questions.difficulty.infraestructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ENUM_DIFFICULTIES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DifficultyJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}
