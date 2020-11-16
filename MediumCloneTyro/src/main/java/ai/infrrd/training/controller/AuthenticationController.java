package ai.infrrd.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.payload.request.SignInRequest;
import ai.infrrd.training.payload.request.SignUpRequest;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.payload.response.SignInResponse;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.security.jwt.JwtUtils;
import ai.infrrd.training.security.services.UserDetailsImplementation;
import ai.infrrd.training.service.SignUpService;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	SignUpService signUpService;
	
	@ApiIgnore
	@GetMapping("/")
	public String basePath() {
		return "Tyro";
		
	}

	@PostMapping("/signin")
	@ApiOperation(value="Login to the system",
	notes="Provide email and password to log-in",
	response=SignInResponse.class)
	public ResponseEntity<?> authenticateUser( @RequestBody SignInRequest signInRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwt);
		return ResponseEntity.ok()
				.headers(headers)
				.body(new SignInResponse( 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail()));
	}

	@PostMapping("/signup")
	@ApiOperation(value="Add a new User",
	notes="Provide email,password and username to sign-up",
	response=MessageResponse.class)
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error","Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error","Email is already in use!"));
		}
		// Create new user's account
		try {
			signUpService.addUser(new UserDto(signUpRequest.getUsername(), 
								signUpRequest.getPassword(),
								signUpRequest.getEmail()));
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error",e.getMessage()));
		}
		return ResponseEntity.ok(new MessageResponse("Success","User registered successfully!"));
	}
}
