package ai.infrrd.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import ai.infrrd.training.payload.request.LoginRequest;
import ai.infrrd.training.payload.request.SignupRequest;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.payload.response.SignInResponse;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.security.jwt.JwtUtils;
import ai.infrrd.training.security.services.UserDetailsImplementation;
import ai.infrrd.training.service.SignUpService;
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
	public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();		
		String authCode="Bearer "+jwt; 
		return ResponseEntity.ok()
				.header("Authorization-code", authCode)
				.body(new SignInResponse( 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
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
					.body(new MessageResponse("Error: "+e.getMessage()));
		}
//		Users user = new Users(signUpRequest.getUsername(), 
//							encoder.encode(signUpRequest.getPassword()),
//							signUpRequest.getEmail());
//		
//		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
