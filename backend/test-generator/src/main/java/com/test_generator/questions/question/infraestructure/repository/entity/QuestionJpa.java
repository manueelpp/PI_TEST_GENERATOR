package com.test_generator.questions.question.infraestructure.repository.entity;


import com.test_generator.questions.difficulty.infraestructure.repository.entity.DifficultyJpa;
import com.test_generator.questions.subjects.subject.infraestructure.repository.entity.SubjectJpa;
import com.test_generator.questions.subjects.topic.infraestructure.repository.entity.TopicJpa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "QUESTIONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  QuestionJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_subject_id")
    private SubjectJpa subject;

    @ManyToOne
    @JoinColumn(name = "fk_topic_id")
    private TopicJpa topic;

    @ManyToOne
    @JoinColumn(name = "fk_difficulty_id")
    private DifficultyJpa difficulty;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(name = "CHOICES", columnDefinition = "TEXT")
    private String choices;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "GENERATED_DATE")
    private LocalDateTime generatedAt;
}
