package security.jpa.ApiRest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import security.jpa.ApiRest.model.CaptureModel;
import security.jpa.ApiRest.repository.CaptureRepository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationUtil {


    private CaptureRepository captureRepository;


    public static ResponseEntity<Object> mapValidationErrors(BindingResult bindingResult,Integer id) {

        if (!bindingResult.hasErrors()) {
            return null;
        }

        Map<String, String> errors = new HashMap<>();

        // Extract each invalid field and its message
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Construct the standard API error response body
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("id", id);
        errorResponse.put("isValid", false);
        errorResponse.put("message", "Validación de datos fallida, revisa los errores");
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
