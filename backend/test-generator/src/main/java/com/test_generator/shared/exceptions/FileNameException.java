package com.test_generator.shared.exceptions;

import static com.test_generator.shared.constants.Constants.VALID_FILES_EXTENSIONS;

public class FileNameException extends RuntimeException
{

    public FileNameException(String fileName) {
        super("Extensión del archivo " + fileName + " inválida. Para que el archivo se considere válido debe ser del tipo: " + VALID_FILES_EXTENSIONS);
    }
}
