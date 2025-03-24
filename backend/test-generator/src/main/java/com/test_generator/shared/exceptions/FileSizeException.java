package com.test_generator.shared.exceptions;

public class FileSizeException extends RuntimeException
{
    public FileSizeException(String fileSize) {
        super("El archivo es demasiado grande: " + fileSize + " Máximo permitido: 10 MB");
    }

    public FileSizeException(String fileName, String fileSize) {
        super("El archivo " + fileName + " es demasiado grande: " + fileSize + " Máximo permitido: 10 MB");
    }
}
