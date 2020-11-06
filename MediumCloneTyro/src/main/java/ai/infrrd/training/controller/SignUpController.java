package ai.infrrd.training.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.RecordNotFoundException;
import ai.infrrd.training.service.UserSignUpService;

@RestController
public class SignUpController {
	
	@Autowired
	UserSignUpService signUpService;
	
	
	
	@RequestMapping(path = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) throws BusinessException, RecordNotFoundException {
		
		signUpService.addUser(user);
		final URI locationPlace = MvcUriComponentsBuilder.fromController(getClass()).path("/user/{username}")
				.buildAndExpand(user.getUsername()).toUri();
		return ResponseEntity.created(locationPlace).body(user);

	}

}
