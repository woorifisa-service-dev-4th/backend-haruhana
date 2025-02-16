package site.haruhana.www.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.haruhana.www.dto.BaseResponse;
import site.haruhana.www.exception.DuplicateEmailException;
import site.haruhana.www.dto.ValidationError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<BaseResponse<Void>> handleDuplicateEmailException(DuplicateEmailException e) {
        BaseResponse<Void> response = BaseResponse.onConflict(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<List<ValidationError>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ValidationError> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ValidationError(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        BaseResponse<List<ValidationError>> response = BaseResponse.onBadRequest("입력값 검증에 실패했습니다.", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
