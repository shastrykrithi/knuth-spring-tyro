package ai.infrrd.training.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.service.UserSignUpService;

@RestController
public class AuthController {

	@Autowired
	UserSignUpService signUpService;

	@PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) throws BusinessException {

		signUpService.addUser(user);
		final URI locationPlace = MvcUriComponentsBuilder.fromController(getClass()).path("/user/{username}")
				.buildAndExpand(user.getUsername()).toUri();
		return ResponseEntity.created(locationPlace).body(user);

	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> getByEmailAndPassword(@RequestBody UserDto user) throws BusinessException {
		String email = user.getEmail();
		String password = user.getPassword();
		ResponseEntity<UserDto> user1 = null;
		user1 = signUpService.getByEmailAndPassword(email, password).map(p -> ResponseEntity.ok(p))
				.orElseThrow(() -> new BusinessException("Requested user not found"));
		return user1;

	}

}
