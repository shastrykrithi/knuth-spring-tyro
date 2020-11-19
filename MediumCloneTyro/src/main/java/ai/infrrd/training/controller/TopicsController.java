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
import ai.infrrd.training.payload.request.TopicFollowRequest;
import ai.infrrd.training.payload.response.ErrorResponse;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.payload.response.SuccessResponse;
import ai.infrrd.training.payload.response.TopicsResponse;
import ai.infrrd.training.repository.TopicRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.TopicsService;
import io.swagger.annotations.ApiOperation;


@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class TopicsController {
	
	@Autowired
	TopicsService topicService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TopicRepository topicRepository;
	
	
	@PostMapping("/topics/follow")
	@ApiOperation(value="User request to follow a topic",
	notes="Provide username and topic id to follow",
	response=MessageResponse.class)
	public ResponseEntity<?> followTopic(@RequestBody TopicFollowRequest topicFollowRequest) {
		if (!userRepository.existsByUsername(topicFollowRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		if (!topicRepository.existsById(topicFollowRequest.getTopicID())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Topic not found")));
		}
		try {
			topicService.followTopic(topicFollowRequest);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
		return ResponseEntity.ok(new SuccessResponse(new MessageResponse("Topic followed by user")));
	}
	
	@PostMapping("/topics/unfollow")
	@ApiOperation(value="User request to unfollow a topic",
	notes="Provide username and topic id to unfollow",
	response=MessageResponse.class)
	public ResponseEntity<?> unfollowTopic(@RequestBody TopicFollowRequest topicFollowRequest) {
		if (!userRepository.existsByUsername(topicFollowRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		if (!topicRepository.existsById(topicFollowRequest.getTopicID())) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Topic not found")));
		}
		try {
			topicService.unfollowTopic(topicFollowRequest);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
		return ResponseEntity.ok(new SuccessResponse(new MessageResponse("Topic un-followed by user")));
	}
	
	@GetMapping("/topics/{userName}")
	@ApiOperation(value="List of topics following",
	notes="Username based following topics list",
	response=MessageResponse.class)
	public ResponseEntity<?> followTopicsList(@PathVariable String userName) {
		if (!userRepository.existsByUsername(userName)){
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		
		try {
			return ResponseEntity.ok().body(new TopicsResponse(topicService.getUserTopics(userName)));
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}
	
	@GetMapping("/topics/auto-fill/{stringMatch}")
	@ApiOperation(value="List of starts-with matching topics",
	notes="Get the list of topics starts-with match",
	response=MessageResponse.class)
	public ResponseEntity<?> startsWith(@PathVariable String stringMatch) {
		try {
			return ResponseEntity.ok().body(new TopicsResponse(topicService.getStringMatchTopics(stringMatch)));
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}
	

}
