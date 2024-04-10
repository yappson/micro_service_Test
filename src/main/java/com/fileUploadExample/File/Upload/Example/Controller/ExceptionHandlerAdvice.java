package com.fileUploadExample.File.Upload.Example.Controller;

import com.fileUploadExample.File.Upload.Example.Config.ProcessingException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ProcessingException.class)
    public ResponseEntity<String> handleProcessingException(ProcessingException ex) {
        return ResponseEntity.status(303).body("ProcessingException occurred: " + ex.getMessage());
    }

    @ExceptionHandler(SkipLimitExceededException.class)
    public ResponseEntity<String> handleSkipLimitExceededException(SkipLimitExceededException ex) {
        return ResponseEntity.status(303).body("SkipLimitExceededException occurred: " + ex.getMessage());
    }


    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<String> handleUnsupportedFormatException(UnsupportedFormatException ex) {
        // 415 Unsupported Media Type is suitable for unsupported formats
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body( ex.getMessage());
    }
}
