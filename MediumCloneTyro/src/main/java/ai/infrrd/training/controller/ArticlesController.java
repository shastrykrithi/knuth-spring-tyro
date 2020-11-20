package ai.infrrd.training.controller;

import java.util.List;

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

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.filter.AuthTokenFilter;
import ai.infrrd.training.payload.request.ArticleRequest;
import ai.infrrd.training.payload.response.ArticleResponse;
import ai.infrrd.training.payload.response.ErrorResponse;
import ai.infrrd.training.payload.response.MessageResponse;
import ai.infrrd.training.payload.response.SuccessResponse;
import ai.infrrd.training.repository.ArticleRepository;
import ai.infrrd.training.repository.UserRepository;
import ai.infrrd.training.service.ArticlesService;
import io.swagger.annotations.ApiOperation;

@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1")
public class ArticlesController {

	@Autowired
	ArticlesService articleService;
	
	@Autowired
	ArticleRepository  articleRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/article/{postID}")
	@ApiOperation(value = "Count number of clicks on a article", response = MessageResponse.class)
	public ResponseEntity<?> articleClick(@PathVariable String postID) {
		if (!articleRepository.existsById(postID)){
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Article not found")));
		}
		
		try {
			ArticlesDto article=articleService.getArticle(postID);
			return ResponseEntity.ok().body(article);
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}

	@GetMapping("/feed")
	@ApiOperation(value = "Get the List of Articles", notes = "The Articles of all users are shown up here", response = ArticleResponse.class)
	public ResponseEntity<?> listOfArticles() {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		try {
			return ResponseEntity.ok().body(new ArticleResponse(articleService.getAllArticles(AuthTokenFilter.currentUser)));
		} catch (BusinessException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}

	@GetMapping("/trending")
	@ApiOperation(value = "Get the Trending Articles", notes = "The Articles with highest clicks are shown up here", response = ArticleResponse.class)
	public ResponseEntity<?> trendingArticles() {
		try {
			List<ArticlesDto> articleList=articleService.getTrendingArticles();
			return ResponseEntity.ok().body(new ArticleResponse(articleList));
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
	}

	@PostMapping("/publish")
	@ApiOperation(value = "Publish the Article", notes = "Write the Post with title and description", response = ArticleResponse.class)
	public ResponseEntity<?> publishArticle(@RequestBody ArticleRequest articleRequest) {
		if (!userRepository.existsByUsername(AuthTokenFilter.currentUser)) {
			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse(new MessageResponse("Username not found")));
		}
		try {
			articleService.postArticle(articleRequest);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(new MessageResponse(e.getMessage())));
		}
		return ResponseEntity.ok(new SuccessResponse(new MessageResponse("Article published")));
	}

}
