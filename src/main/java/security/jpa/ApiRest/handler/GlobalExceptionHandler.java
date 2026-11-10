package security.jpa.ApiRest.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // CASO A: Captura errores cuando fallan las entidades al persistir (Hibernate)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String propertyName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(propertyName, errorMessage);
        });

        return buildErrorResponse("Error de validación en la base de datos", errors);
    }

    // CASO B: Captura errores automáticos de Spring si decides quitar 'BindingResult' en el futuro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return buildErrorResponse("Validación de datos de entrada fallida", errors);
    }

    @ExceptionHandler(DuplicateUsername.class)
    public ResponseEntity<Object> handleDuplicateResourceException(
            DuplicateUsername exception, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();


        errors.put("message", exception.getMessage());


        return  buildErrorResponse("error", errors);


    }
    // Método auxiliar reutilizable para estructurar el JSON idéntico siempre no se usa
    private ResponseEntity<Object> buildErrorResponse(String message, Map<String, String> errors) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("isValid", false);
        errorResponse.put("message", message);
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
