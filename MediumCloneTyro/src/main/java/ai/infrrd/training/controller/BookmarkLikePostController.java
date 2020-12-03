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
import ai.infrrd.training.repository.ArticleRepository;
import ai.infrrd.training.repository.TopicRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.ArticlesService;
import ai.infrrd.training.service.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class BookmarkLikePostController {

	private static final Logger logger = LoggerFactory.getLogger(ArticlesController.class);

	@Autowired
	ArticlesService articleService;

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TopicRepository topicRepository;

	@Autowired
	ResponseModel responseModel;

	@PostMapping("/article/bookmark")
	@ApiOperation(value = "User request to bookmark article", notes = "Provide postID to bookmark", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel bookMarkArticle(@RequestBody @Valid IDRequestModel idRequestModel, BindingResult bindingResult)
			throws BusinessException {

		validateRequest(idRequestModel, bindingResult);

		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!articleRepository.existsById(idRequestModel.getId())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Article not found");
		}
		try {
			articleService.bookmarkArticle(idRequestModel, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Article bookmarked");
		return responseModel;
	}

	@PostMapping("/article/like")
	@ApiOperation(value = "User request to bookmark article", notes = "Provide postID to bookmark", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel likeArticle(@RequestBody @Valid IDRequestModel idRequestModel, BindingResult bindingResult)
			throws BusinessException {

		validateRequest(idRequestModel, bindingResult);

		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!articleRepository.existsById(idRequestModel.getId())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Article not found");
		}
		try {
			articleService.likeArticle(idRequestModel, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Article liked");
		return responseModel;
	}

	@GetMapping("/bookmarks")
	@ApiOperation(value = "List of bookmarks", notes = "User bookmarks list", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel followTopicsList() throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}

		try {
			responseModel.setData("result", articleService.getUserBookMarks(AuthTokenFilter.currentUser));
			return responseModel;
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;
		}
	}

	@PostMapping("/bookmarks/remove")
	@ApiOperation(value = "User request to bookmark article", notes = "Provide postID to bookmark", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel removeBookmark(@RequestBody @Valid IDRequestModel idRequestModel, BindingResult bindingResult)
			throws BusinessException {

		validateRequest(idRequestModel, bindingResult);

		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!articleRepository.existsById(idRequestModel.getId())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Article not found");
		}
		try {
			articleService.removeBookmark(idRequestModel, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Removed from bookmarks!!");
		return responseModel;
	}

	@PostMapping("/like/remove")
	@ApiOperation(value = "User request to unlike article", notes = "Provide postID to unlike", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel unlikeArticle(@RequestBody @Valid IDRequestModel idRequestModel, BindingResult bindingResult)
			throws BusinessException {

		validateRequest(idRequestModel, bindingResult);

		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		if (!articleRepository.existsById(idRequestModel.getId())) {
			logger.error("User to follow not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Article not found");
		}
		try {
			articleService.unlikeArticle(idRequestModel, AuthTokenFilter.currentUser);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;

		}
		responseModel.setData("result", "Article un-liked");
		return responseModel;
	}

	private void validateRequest(@RequestBody @Valid IDRequestModel idRequestModel, BindingResult bindingResult)
			throws BusinessException {
		if (idRequestModel == null) {
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
