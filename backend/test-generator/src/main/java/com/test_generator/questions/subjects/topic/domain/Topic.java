package com.test_generator.questions.subjects.topic.domain;

import com.test_generator.questions.subjects.subject.domain.Subject;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {

    private Integer id;

    private String name;

    private Subject subject;
}
