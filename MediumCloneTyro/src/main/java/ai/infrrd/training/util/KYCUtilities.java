package ai.infrrd.training.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class KYCUtilities {

	public static Map<String, Object> getFieldErrorResponse(BindingResult result) {
		Map<String, Object> fieldError = new HashMap<String, Object>();
		List<FieldError> errors = result.getFieldErrors();
		for (FieldError error : errors) {
			fieldError.put(error.getField(), error.getDefaultMessage());
		}
		return fieldError;
	}
}
