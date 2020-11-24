package ai.infrrd.training.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.filter.AuthTokenFilter;
import ai.infrrd.training.model.Articles;
import ai.infrrd.training.model.Topics;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.ArticleRequest;
import ai.infrrd.training.payload.request.TopicFollowRequest;
import ai.infrrd.training.repository.ArticleRepository;
import ai.infrrd.training.repository.TopicRepository;
import ai.infrrd.training.repository.UserRepository;

@Service
public class ArticlesService {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserRepository userRepo;

	@Autowired
	TopicRepository topicRepository;

	public List<ArticlesDto> getAllArticles(String username) throws MessageException {
		HashSet<UserDto> followers = userRepo.findByUsername(username).getFollowing();
		List<ArticlesDto> recentArticlesList = new ArrayList<ArticlesDto>();

		if (followers != null) {
			if (!followers.isEmpty()) {
				for (UserDto element : followers) {
					recentArticlesList.addAll(articleRepository.findByUserName(element.getUsername()));
				}
			} else
				throw new MessageException("User is not following anyone yet!!!");

		} else {
			throw new MessageException("User is not following anyone yet!!!");
		}
		if (!recentArticlesList.isEmpty()) {
			Collections.sort(recentArticlesList);
		} else {
			throw new MessageException("No articles in feed yet!!!");
		}

		return recentArticlesList;
	}

	public List<ArticlesDto> getTrendingArticles() throws MessageException {

		List<ArticlesDto> articles = new ArrayList<ArticlesDto>();
		List<Articles> sortedArticles = articleRepository.findTop4ByOrderByViewsDesc();

		if (!sortedArticles.isEmpty()) {
			for (Articles element : sortedArticles) {
				ArticlesDto articleDto = new ArticlesDto(element.getId(), element.getPostTitle(),
						element.getPostDescription(), element.getTimestamp(), element.getViews(), element.getUser());
				articles.add(articleDto);
			}
		} else {
			throw new MessageException("Articles list is empty");
		}
		return articles;
	}

	public ArticlesDto getArticle(String postID) throws MessageException {
		Optional<Articles> optionalArticle = articleRepository.findById(postID);
		Articles article = new Articles();

		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
			article.setViews(article.getViews() + 1);
			articleRepository.save(article);
		}
		optionalArticle = articleRepository.findById(postID);
		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
		}
		return new ArticlesDto(article.getId(), article.getPostTitle(), article.getPostDescription(),
				article.getTimestamp(), article.getViews(), article.getUser());
	}

	public boolean postArticle(ArticleRequest articleRequest) throws MessageException {
		Articles article = new Articles();
		HashSet<TopicFollowRequest> topicsList = new HashSet<TopicFollowRequest>();

		Users user = userRepo.findByUsername(AuthTokenFilter.currentUser);

		for (TopicFollowRequest element : articleRequest.getTopics()) {
			Optional<Topics> optionalTopic = topicRepository.findById(element.getTopicID());
			if (optionalTopic.isPresent()) {
				topicsList.add(new TopicFollowRequest(optionalTopic.get().getId()));
			} else {
				throw new MessageException("Topic not found!!");
			}

		}

		article.setPostTitle(articleRequest.getPostTitle());
		article.setPostDescription(articleRequest.getPostDescription());
		article.setTopics(topicsList);

		article.setUser(new UserDto(user.getId(), user.getUsername()));

		article.setTimestamp(Instant.now().toEpochMilli());

		articleRepository.save(article);
		return true;

	}
}
