package com.test_generator.chat.controller;

import com.test_generator.chat.services.CsvProcessor;
import com.test_generator.chat.services.OpenWebUiService;
import com.test_generator.questions.question.application.mapper.QuestionMapper;
import com.test_generator.questions.question.domain.Question;
import com.test_generator.questions.question.infraestructure.dtos.output.QuestionOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.test_generator.shared.constants.Constants.DEFAULT_TEST_PROMPT;
import static com.test_generator.shared.constants.Constants.VALID_FILES_EXTENSIONS;

@RestController

@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OpenWebUiController {

    private final OpenWebUiService openWebUiService;
    private final CsvProcessor csvProcessor;

    private final QuestionMapper questionMapper;

    @PostMapping("/tunning")
    public ResponseEntity<String> outputSample(@RequestParam String prompt) throws IOException {

        String apiResponse = openWebUiService.sendPrompt(prompt, null);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/ask")
    public ResponseEntity<List<QuestionOutputDto>> askQuestion(@RequestParam String prompt) throws IOException {

        String apiResponse = openWebUiService.sendPrompt(prompt, null);
        List<Question> generatedQuestions = csvProcessor.extractQuestions(apiResponse);

        return ResponseEntity.ok(getQuestionOutputDto(generatedQuestions));
    }

    @Operation(summary = "Subir un archivo para que Ollama lo analice", description = "Archivo a procesar, debe ser del tipo: " + VALID_FILES_EXTENSIONS)
    @PostMapping(value = "/ask-with-file", consumes = "multipart/form-data")
    public ResponseEntity<List<QuestionOutputDto>> askWithFile(@RequestParam(defaultValue = DEFAULT_TEST_PROMPT) String prompt,
                                              @RequestParam("file") MultipartFile file) throws IOException {

        String apiResponse = openWebUiService.sendPrompt(prompt, file);
        List<Question> generatedQuestions = csvProcessor.extractQuestions(apiResponse);

        return ResponseEntity.ok(getQuestionOutputDto(generatedQuestions));
    }

    @GetMapping("/service-status")
    public ResponseEntity<String> isServiceAvailable() {

        return ResponseEntity.ok(openWebUiService.checkServiceStatus());
    }

    private List<QuestionOutputDto> getQuestionOutputDto(List<Question> questions){

        return questions.stream()
                .map(questionMapper::domainToOutput)
                .toList();
    }

}
