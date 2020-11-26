package ai.infrrd.training.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
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
import ai.infrrd.training.payload.response.SignInResponse;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.security.jwt.JwtUtils;
import ai.infrrd.training.security.services.UserDetailsImplementation;
import ai.infrrd.training.service.ResponseModel;
import ai.infrrd.training.service.SignUpService;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "*", exposedHeaders = "${Access-Control-Expose-Headers}", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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

	@Autowired
	ResponseModel responseModel;

	@Autowired
	MessageSource messageSource;

	@ApiIgnore
	@GetMapping("/")
	public String basePath() {
		return "Tyro";

	}

	@PostMapping("/signin")
	@ApiOperation(value = "Login to the system", notes = "Provide email and password to log-in", response = SignInResponse.class)
	public ResponseModel authenticateUser(@Valid @RequestBody SignInRequest signInRequest, HttpServletResponse response,
			BindingResult result) throws BusinessException {

		validateSignInRequest(signInRequest, result);

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();

		response.addHeader("Authorization", "Bearer " + jwt);
		responseModel.setData("result",
				new SignInResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
		return responseModel;
	}

	@PostMapping("/signup")
	@ApiOperation(value = "Add a new User", notes = "Provide email,password and username to sign-up", response = ResponseModel.class)
	public ResponseModel registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result)
			throws BusinessException {

		validateSignUpRequest(signUpRequest, result);

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			logger.error("Username already taken");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Username already taken!!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			logger.error("Email is already in use!");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Email is already in use!");
		}

		try {
			signUpService.addUser(
					new UserDto(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail()));
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		responseModel.setData("result", "User registered successfully");
		return responseModel;
	}

	private void validateSignUpRequest(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult)
			throws BusinessException {
		if (signUpRequest == null) {
			logger.error("Empty request object");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No details sent in the request object.");
		}
		if (bindingResult.hasFieldErrors()) {
			String errorMessage = bindingResult.getFieldError().getDefaultMessage();
			logger.error("Received request with invalid arguments. [ErrorMessage={}]", errorMessage);
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid arguments");
		}
	}

	private void validateSignInRequest(@RequestBody @Valid SignInRequest signInRequest, BindingResult bindingResult)
			throws BusinessException {
		if (signInRequest == null) {
			logger.error("Empty request object");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No details sent in the request object.");
		}
		if (bindingResult.hasFieldErrors()) {
			String errorMessage = bindingResult.getFieldError().getDefaultMessage();
			logger.error("Received request with invalid arguments. [ErrorMessage={}]", errorMessage);
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid arguments");
		}
	}

}
