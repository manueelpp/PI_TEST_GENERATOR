package com.test_generator.chat.services.impl;

import com.test_generator.chat.services.OpenWebUiService;
import com.test_generator.shared.exceptions.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.test_generator.shared.constants.Constants.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class OpenWebUiServiceImpl implements OpenWebUiService {

    @Value("${openwebui.api.url}")
    private String openWebUIUrl;

    @Value("${openwebui.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final int MAX_GENERATION_ATTEMPTS = 2;
    private int attempts = 0;

    @Override
    public String sendPrompt(String prompt, MultipartFile file) {
        if (!isServiceAvailable()) {
            throw new ApiException(OPENWEBUI_NOT_AVAILABLE);
        }

        return responseFilter(prompt, file);
    }

    @Override
    public String checkServiceStatus() {
        if (!isServiceAvailable()) {
            throw new ApiException(OPENWEBUI_NOT_AVAILABLE);
        }

        return OPENWEBUI_AVAILABLE;
    }

    public String responseFilter(String prompt, MultipartFile file) {

        String response;

        try {
            String fileId = (Objects.nonNull(file)) ? uploadFile(file) : null;

            Map<String, Object> requestBody = buildRequestBody(
                    generatePromptInstructions(prompt, file),
                    fileId
            );

            response = sendChatRequest(requestBody);
        } catch (Exception exception) {

            log.error(EXCEPTION_HAS_OCURRED, exception.getMessage(), exception);

            if (++attempts >= MAX_GENERATION_ATTEMPTS) {
                throw new ApiException(RESPONSE_GENERATION_FAILURE);
            }
            return sendPrompt(prompt, file);
        }

        if (StringUtils.isBlank(response) || !response.contains("::"))
            throw new ApiException(RESPONSE_GENERATION_FAILURE);

        return response;
    }

    private String generatePromptInstructions(String prompt, MultipartFile file) {
        return String.format(file != null ? PROMPT_WITH_FILES_TEMPLATE : PROMPT_WITHOUT_FILES_TEMPLATE, prompt);
    }

    private Map<String, Object> buildRequestBody(String prompt, String fileId) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put(MODEL, OLLAMA_MODEL);
        requestBody.put(MESSAGES, List.of(Map.of(ROLE, USER, CONTENT, prompt)));

        if (fileId != null) {
            requestBody.put(FILES, List.of(Map.of(TYPE, FILE, ID, fileId)));
        }

        requestBody.put(SYSTEM, "Forget previous context.");
        return requestBody;
    }

    private String sendChatRequest(Map<String, Object> requestBody) {

        String chatUrl = openWebUIUrl + OPENWEBUI_CHAT_REQUEST_URL;
        HttpHeaders headers = createAuthHeaders();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map<String, Object>> response =
                    restTemplate.exchange(
                            chatUrl,
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<>() {}
                    );

            return extractResponseMessage(response);
        } catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException.Unauthorized) {
                throw new ApiException(UNAUTHORIZED_EXCEPTION_OPENWEBUI + exception.getLocalizedMessage());
            }
            throw new ApiException(ERROR_SENDING_REQUEST_TO_OPENWEBUI + exception.getLocalizedMessage());
        }

    }

    private String extractResponseMessage(ResponseEntity<Map<String, Object>> response) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) Optional.ofNullable(response.getBody())
                .map(body -> body.get(CHOICES))
                .orElse(Collections.emptyList());

        if (choices.isEmpty()) {
            throw new ApiException(RESPONSE_GENERATION_FAILURE);
        }

        Map<String, Object> firstChoice = choices.getFirst();
        if (firstChoice == null) {
            throw new ApiException(RESPONSE_GENERATION_FAILURE);
        }

        Map<String, Object> message = (Map<String, Object>) firstChoice.get(MESSAGE);
        if (message == null || !message.containsKey(CONTENT)) {
            throw new ApiException(RESPONSE_GENERATION_FAILURE);
        }

        return (String) message.get(CONTENT);

    }

    private String uploadFile(MultipartFile file) throws IOException {
        String uploadUrl = openWebUIUrl + OPENWEBUI_UPLOAD_FILE_REQUEST_URL;

        HttpHeaders headers = createAuthHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE, new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, Map.class);

        return Optional.ofNullable(response.getBody())
                .map(bodyMap -> (String) bodyMap.get(ID))
                .orElseThrow(() -> new ApiException(API_UPLOAD_FILE_FAILED));
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public Boolean isServiceAvailable() {
        String healthCheckUrl = openWebUIUrl + OPENWEBUI_STATUS_URL;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(healthCheckUrl, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            return false;
        }
    }
}
