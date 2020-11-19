package ai.infrrd.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.payload.request.FollowerRequest;
import ai.infrrd.training.payload.response.ErrorResponse;
import ai.infrrd.training.payload.response.FollowerResponse;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.payload.response.SuccessResponse;
import ai.infrrd.training.repository.FollowerRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.FollowersService;
import io.swagger.annotations.ApiOperation;


@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class FollowersController {
	
	@Autowired
	FollowersService followersService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FollowerRepository followerRepository;
	
	
	@PostMapping("/people/follow")
	@ApiOperation(value="User request to follow a user",
	notes="Provide username and user id to follow",
	response=MessageResponse.class)
	public ResponseEntity<?> followTopic(@RequestBody FollowerRequest followerRequest) {
		if (!userRepository.existsByUsername(followerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		if (!userRepository.existsById(followerRequest.getFollowRequestID())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("To follow User not found")));
		}
		try {
			followersService.followUser(followerRequest);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
		return ResponseEntity.ok(new SuccessResponse(new MessageResponse("User is followed")));
	}
	
	@PostMapping("/people/unfollow")
	@ApiOperation(value="User request to unfollow a user",
	notes="Provide username and user id to unfollow",
	response=MessageResponse.class)
	public ResponseEntity<?> unfollowTopic(@RequestBody FollowerRequest followerRequest) {
		if (!userRepository.existsByUsername(followerRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		if (!userRepository.existsById(followerRequest.getFollowRequestID())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("To un-follow User not found")));
		}
		try {
			followersService.unfollowUser(followerRequest);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
		return ResponseEntity.ok(new SuccessResponse(new MessageResponse("User is un-followed")));
	}
	
	@GetMapping("/people/{userName}")
	@ApiOperation(value="List of followers",
	notes="Username based following followers list",
	response=MessageResponse.class)
	public ResponseEntity<?> followerList(@PathVariable String userName) {
		if (!userRepository.existsByUsername(userName)){
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		try {
			return ResponseEntity.ok().body(new FollowerResponse(followersService.getUserFollowers(userName)));
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}
	
	
	

}
