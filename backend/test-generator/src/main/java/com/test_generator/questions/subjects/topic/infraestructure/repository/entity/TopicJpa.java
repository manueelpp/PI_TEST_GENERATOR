package com.test_generator.questions.subjects.topic.infraestructure.repository.entity;

import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TOPICS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subject_id", referencedColumnName = "id", nullable = false)
    private SubjectJpa subject;
}
