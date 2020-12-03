package ai.infrrd.training.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.filter.AuthTokenFilter;
import ai.infrrd.training.payload.request.IDRequestModel;
import ai.infrrd.training.repository.NotificationRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.ArticlesService;
import ai.infrrd.training.service.PushNotificationService;
import ai.infrrd.training.service.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class NotificationsController {
	
	private static final Logger logger = LoggerFactory.getLogger(FollowersController.class);

	@Autowired
	ResponseModel responseModel;

	@Autowired
	ArticlesService articlesService;
	
	@Autowired
	PushNotificationService pushNotificationService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@PostMapping("/notification/read")
	@ApiOperation(value = "Make notification as read", notes = "notification id to make it read", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel followTopic(@RequestBody @Valid IDRequestModel notificationId, BindingResult bindingResult) throws BusinessException {
		
		validateRequest(notificationId,bindingResult);
		
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!notificationRepository.existsById(notificationId.getId())) {
			logger.error("Notification with id not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Notification with id not found");
		}
		try {
			articlesService.notificationRead(notificationId, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Notification Read");
		return responseModel;
	}
	
	
	@PostMapping("/notification/activate")
	@ApiOperation(value = "Activate push notification", notes = "device token", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel activateNotification(@RequestBody IDRequestModel notificationId) throws BusinessException {
		
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			pushNotificationService.activateNotification(notificationId, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Notification Feature Activated");
		return responseModel;
	}
	
	@PostMapping("/notification/deActivate")
	@ApiOperation(value = "deActivate push notification", notes = "device token", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel deActivateNotification() throws BusinessException {
		
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			pushNotificationService.deActivateNotification(AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Notification Feature De-Activated");
		return responseModel;
	}
	
	@GetMapping("/notifications")
	@ApiOperation(value = "Get the List of notifications", notes = "The notifications for user is shown here", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel listOfNotifications() throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			responseModel.setData("result", pushNotificationService.getAllNotifications(AuthTokenFilter.currentUser));
			return responseModel;
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;
		}
	}
	
	private void validateRequest(@RequestBody @Valid IDRequestModel notificationId, BindingResult bindingResult)
			throws BusinessException {
		if (notificationId == null) {
			logger.error("Empty request object");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "No details sent in the request object.");
		}
		if (bindingResult.hasFieldErrors()) {
			String errorMessage = bindingResult.getFieldError().getDefaultMessage();
			logger.error("Received request with invalid arguments. [ErrorMessage={}]", errorMessage);
			throw new BusinessException(HttpStatus.BAD_REQUEST,  "Invalid arguments");
		}

	}


}
