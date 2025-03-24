package com.test_generator.chat.services.impl;

import com.test_generator.chat.services.CsvProcessor;
import com.test_generator.questions.question.application.PostQuestionService;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.input.QuestionInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CsvProcessorImpl implements CsvProcessor {

    private final PostQuestionService postQuestionService;

    @Override
    public List<Question> extractQuestions(String csvContent) {
        List<QuestionInputDto> questions = new ArrayList<>();
        String[] lines = csvContent.split("\n");

        // Encontrar la primera línea perteneciente al formato del CSV. En principio, debería ser la cabecera
        int firstIndex = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("::")) {
                firstIndex = i + 1; // +1 para saltar la cabecera
                break;
            }
        }

        int lastIndex = lines.length;
        for (int i = lines.length - 1; i > firstIndex; i--) {
            if (lines[i].contains("::")) {
                lastIndex = i + 1;
                break;
            }
        }

        // Procesar desde la línea encontrada hasta la última válida
        for (int i = firstIndex; i < lastIndex; i++) {
            String[] fields = lines[i].split("::");

            if (fields.length < 4) continue; // Saltar líneas inválidas

            String subject = fields[0].trim();
            String topic = fields[1].trim();
            String difficulty = fields[2].trim();
            String question = fields[3].trim();

            // Manejar opciones, algunas preguntas pueden no tenerlas
            List<String> options = Arrays.stream(Arrays.copyOfRange(fields, 4, Math.min(fields.length, 8)))
                    .map(String::trim)
                    .filter(opt -> !opt.isEmpty())
                    .collect(Collectors.toList());

            // Manejar la respuesta correcta si existe
            String correctAnswer = fields.length > 8 ? fields[8].trim() : "";

            questions.add(new QuestionInputDto(subject, topic, difficulty, question, options, correctAnswer));
        }

        return questions.stream()
                .map(postQuestionService::createQuestion)
                .toList();
    }
}
