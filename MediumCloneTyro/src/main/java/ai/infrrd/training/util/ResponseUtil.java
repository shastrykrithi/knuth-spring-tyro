package ai.infrrd.training.util;

import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

	final static public ResponseEntity<Object> fieldErrorResponse(String message, Object fieldError) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", false);
		map.put("data", null);
		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("message", message);
		map.put("timestamp", Instant.now().toEpochMilli());
		map.put("fieldError", fieldError);
		return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
	}
}
