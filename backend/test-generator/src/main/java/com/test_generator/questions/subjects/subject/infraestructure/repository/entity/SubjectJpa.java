package com.test_generator.questions.subjects.subject.infraestructure.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SUBJECTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}
