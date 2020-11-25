package ai.infrrd.training.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.filter.AuthTokenFilter;
import ai.infrrd.training.payload.request.ArticleRequest;
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
public class ArticlesController {

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

	@GetMapping("/article/{postID}")
	@ApiOperation(value = "Count number of clicks on a article", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel articleClick(@PathVariable String postID) throws BusinessException {
		if (!articleRepository.existsById(postID)) {
			logger.error("Article not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Article not found");
		}

		try {
			ArticlesDto article = articleService.getArticle(postID,AuthTokenFilter.currentUser);
			responseModel.setData("result", article);
			return responseModel;
		} catch (MessageException e) {
			logger.error(e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/feed")
	@ApiOperation(value = "Get the List of Articles", notes = "The Articles of all users are shown up here", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel listOfArticles() throws BusinessException {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			responseModel.setData("result", articleService.getAllArticles(AuthTokenFilter.currentUser));
			return responseModel;
		} catch (MessageException e) {
			logger.error(e.getMessage());
			responseModel.setData("error", e.getMessage());
			return responseModel;
		}
	}

	@GetMapping("/trending")
	@ApiOperation(value = "Get the Trending Articles", notes = "The Articles with highest clicks are shown up here", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel trendingArticles() throws BusinessException {
		try {
			List<ArticlesDto> articleList = articleService.getTrendingArticles();
			responseModel.setData("result", articleList);
			return responseModel;
		} catch (MessageException e) {
			logger.error(e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/publish")
	@ApiOperation(value = "Publish the Article", notes = "Write the Post with title and description", authorizations = {
			@Authorization(value = "jwtToken") }, response = ResponseModel.class)
	public ResponseModel publishArticle(@Valid @RequestBody ArticleRequest articleRequest, BindingResult result)
			throws BusinessException {

		validateRequest(articleRequest, result);

		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			logger.error("User not found");
			throw new BusinessException(HttpStatus.BAD_REQUEST, "User not found");
		}
		try {
			articleService.postArticle(articleRequest);
		} catch (MessageException e) {
			logger.error(e.getMessage());
			throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		responseModel.setData("result", "Article published");
		return responseModel;
	}

	private void validateRequest(@RequestBody @Valid ArticleRequest articleRequest, BindingResult bindingResult)
			throws BusinessException {
		if (articleRequest == null) {
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
