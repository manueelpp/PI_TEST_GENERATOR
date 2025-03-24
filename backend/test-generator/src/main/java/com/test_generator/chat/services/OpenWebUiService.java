package com.test_generator.chat.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OpenWebUiService {

    String sendPrompt(String prompt, MultipartFile file) throws IOException;

    String checkServiceStatus();
}
