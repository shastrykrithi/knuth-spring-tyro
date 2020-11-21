package ai.infrrd.training.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.filter.AuthTokenFilter;
import ai.infrrd.training.payload.request.FollowerRequest;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.repository.FollowerRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.FollowersService;
import ai.infrrd.training.service.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class FollowersController {

	private static final Logger logger = LoggerFactory.getLogger(FollowersController.class);

	@Autowired
	ResponseModel responseModel;

	@Autowired
	FollowersService followersService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FollowerRepository followerRepository;

	@PostMapping("/people/follow")
	@ApiOperation(value = "User request to follow a user", notes = "Provide username and user id to follow", authorizations = {
			@Authorization(value = "jwtToken") }, response = MessageResponse.class)
	public ResponseModel followTopic(@RequestBody FollowerRequest followerRequest) throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!userRepository.existsById(followerRequest.getFollowRequestID())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User to follow not found");
		}
		try {
			followersService.followUser(followerRequest, AuthTokenFilter.currentUser);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "User followed");
		return responseModel;
	}

	@PostMapping("/people/unfollow")
	@ApiOperation(value = "User request to unfollow a user", notes = "Provide username and user id to unfollow", authorizations = {
			@Authorization(value = "jwtToken") }, response = MessageResponse.class)
	public ResponseModel unfollowTopic(@RequestBody FollowerRequest followerRequest) throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!userRepository.existsById(followerRequest.getFollowRequestID())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User to un-follow not found");
		}
		try {
			followersService.unfollowUser(followerRequest, AuthTokenFilter.currentUser);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "User un-followed");
		return responseModel;
	}

	@GetMapping("/people")
	@ApiOperation(value = "List of followers", notes = "Username based following followers list", authorizations = {
			@Authorization(value = "jwtToken") }, response = MessageResponse.class)
	public ResponseModel followerList() throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			responseModel.setData("result", followersService.getUserFollowers(AuthTokenFilter.currentUser));
			return responseModel;
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;
		}
	}

}
