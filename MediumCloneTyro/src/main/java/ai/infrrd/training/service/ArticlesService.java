package ai.infrrd.training.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.dto.NotificationsDto;
import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Articles;
import ai.infrrd.training.model.Notifications;
import ai.infrrd.training.model.Topics;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.ArticleRequest;
import ai.infrrd.training.payload.request.IDRequestModel;
import ai.infrrd.training.payload.response.PushNotificationResponse;
import ai.infrrd.training.repository.ArticleRepository;
import ai.infrrd.training.repository.NotificationRepository;
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

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	PushNotificationService pushNotificationService;

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

	public ArticlesDto getArticle(String postID, String username) throws MessageException {
		boolean isLiked = false;
		boolean isBookmarked = false;

		Optional<Articles> optionalArticle = articleRepository.findById(postID);
		Users user = userRepo.findByUsername(username);

		HashSet<String> list = new HashSet<String>();
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

		if (user.getBookmarks() != null) {
			list = user.getBookmarks();
			if (list.contains(article.getId())) {
				isBookmarked = true;
			}
		}

		if (user.getLiked() != null) {
			list = user.getLiked();
			if (list.contains(article.getId())) {
				isLiked = true;
			}
		}

		return new ArticlesDto(article.getId(), article.getPostTitle(), article.getPostDescription(),
				article.getTimestamp(), article.getViews(), article.getLikes(), isBookmarked, isLiked,
				article.getUser());
	}

	public boolean postArticle(ArticleRequest articleRequest, String username) throws MessageException {
		long timestamp = Instant.now().toEpochMilli();
		Articles article = new Articles();
		HashSet<IDRequestModel> topicsList = new HashSet<IDRequestModel>();

		Users user = userRepo.findByUsername(username);
		HashSet<UserDto> followingList = new HashSet<UserDto>();
		if (user.getFollowedBy() != null) {
			followingList = user.getFollowedBy();
		}

		for (IDRequestModel element : articleRequest.getTopics()) {
			Optional<Topics> optionalTopic = topicRepository.findById(element.getId());
			if (optionalTopic.isPresent()) {
				topicsList.add(new IDRequestModel(optionalTopic.get().getId()));
			} else {
				throw new MessageException("Topic not found!!");
			}

		}

		article.setPostTitle(articleRequest.getPostTitle());
		article.setPostDescription(articleRequest.getPostDescription());
		article.setTopics(topicsList);

		article.setUser(new UserDto(user.getId(), user.getUsername()));

		article.setTimestamp(timestamp);
		articleRepository.save(article);

		if (articleRepository.existsByTimestamp(timestamp)) {

			// set notification for user followers

			if (!followingList.isEmpty()) {
				Notifications notification = new Notifications("publish", username, article.getId(),
						article.getPostTitle(), article.getTimestamp());
				notificationRepository.save(notification);
				for (UserDto follower : followingList) {

					if (userRepo.existsByUsername(follower.getUsername())) {
						HashSet<NotificationsDto> notificationList = new HashSet<NotificationsDto>();
						Users followeruser = userRepo.findByUsername(follower.getUsername());
						if (followeruser.getNotifications() != null) {
							notificationList = user.getNotifications();

						}

						Notifications currentNotification = notificationRepository.findByNotifyforAndPostId("publish",
								article.getId());
						if (currentNotification.getId() != null) {
							notificationList.add(new NotificationsDto(currentNotification.getId(), false));
						}
						followeruser.setNotifications(notificationList);
						userRepo.save(followeruser);

						if (followeruser.getDeviceToken() != null) {
							PushNotificationResponse pushNotificationResponse = new PushNotificationResponse();
							pushNotificationResponse.setTarget(followeruser.getDeviceToken());
							pushNotificationResponse.setTitle("publish");
							pushNotificationResponse.setBody(currentNotification.toString());
							pushNotificationService.sendPushNotificationToDevice(pushNotificationResponse);
						}
					}

				}

			}

			// set notification for topic followers

			for (IDRequestModel topic : topicsList) {

				Optional<Topics> optionalTopic = topicRepository.findById(topic.getId());
				if (optionalTopic.isPresent()) {
					Notifications notification = new Notifications("topic", optionalTopic.get().getTopicName(),
							article.getId(), article.getPostTitle(), article.getTimestamp());
					notificationRepository.save(notification);

					for (UserDto follower : optionalTopic.get().getUsers()) {

						if (userRepo.existsByUsername(follower.getUsername())) {
							HashSet<NotificationsDto> notificationList = new HashSet<NotificationsDto>();
							Users followeruser = userRepo.findByUsername(follower.getUsername());
							if (followeruser.getNotifications() != null) {
								notificationList = followeruser.getNotifications();

							}

							Notifications currentNotification = notificationRepository.findByNotificationNameAndPostId(
									optionalTopic.get().getTopicName(), article.getId());
							if (currentNotification.getId() != null) {
								notificationList.add(new NotificationsDto(currentNotification.getId(), false));
							}
							followeruser.setNotifications(notificationList);
							userRepo.save(followeruser);

							if (followeruser.getDeviceToken() != null) {
								PushNotificationResponse pushNotificationResponse = new PushNotificationResponse();
								pushNotificationResponse.setTarget(followeruser.getDeviceToken());
								pushNotificationResponse.setTitle("topic");
								pushNotificationResponse.setBody(currentNotification.toString());
								pushNotificationService.sendPushNotificationToDevice(pushNotificationResponse);
							}

						}
					}

				} else {
					throw new MessageException("Topic not found!!");
				}

			}

		}

		return true;

	}

	public boolean bookmarkArticle(IDRequestModel idRequestModel, String currentUser) throws MessageException {
		Articles article = new Articles();
		HashSet<String> bookmarkList = new HashSet<String>();

		Optional<Articles> optionalArticle = articleRepository.findById(idRequestModel.getId());

		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
			article.setBookmarkCount(article.getBookmarkCount() + 1);
			articleRepository.save(article);
		}

		Users user = userRepo.findByUsername(currentUser);

		if (user.getBookmarks() != null) {
			bookmarkList = user.getBookmarks();
		}
		bookmarkList.add(idRequestModel.getId());
		user.setBookmarks(bookmarkList);
		userRepo.save(user);
		return true;

	}

	public boolean likeArticle(IDRequestModel idRequestModel, String currentUser) throws MessageException {
		Articles article = new Articles();
		HashSet<String> likedList = new HashSet<String>();
		HashSet<NotificationsDto> notificationList = new HashSet<NotificationsDto>();

		Optional<Articles> optionalArticle = articleRepository.findById(idRequestModel.getId());

		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
			article.setLikes(article.getLikes() + 1);
			articleRepository.save(article);

			// set notification
			Notifications notification = new Notifications("like", currentUser, article.getId(), article.getPostTitle(),
					article.getTimestamp());
			notificationRepository.save(notification);
			if (userRepo.existsByUsername(article.getUser().getUsername())) {
				Users user = userRepo.findByUsername(article.getUser().getUsername());
				if (user.getNotifications() != null) {
					notificationList = user.getNotifications();

				}

				Notifications currentNotification = notificationRepository
						.findByNotifyforAndPostIdAndNotificationName("like", article.getId(), currentUser);
				if (currentNotification.getId() != null) {
					notificationList.add(new NotificationsDto(currentNotification.getId(), false));
				}
				user.setNotifications(notificationList);
				userRepo.save(user);

				if (user.getDeviceToken() != null) {
					PushNotificationResponse pushNotificationResponse = new PushNotificationResponse();
					pushNotificationResponse.setTarget(user.getDeviceToken());
					pushNotificationResponse.setTitle("like");
					pushNotificationResponse.setBody(currentNotification.toString());
					pushNotificationService.sendPushNotificationToDevice(pushNotificationResponse);
				}

			}
		}
		Users user = userRepo.findByUsername(currentUser);

		if (user.getLiked() != null) {
			likedList = user.getLiked();
		}
		likedList.add(idRequestModel.getId());
		user.setLiked(likedList);
		userRepo.save(user);

		return true;

	}

	public List<ArticlesDto> getUserBookMarks(String currentUser) throws MessageException {

		List<ArticlesDto> articles = new ArrayList<ArticlesDto>();
		HashSet<String> bookmarkList = new HashSet<String>();
		Articles articleInfo = new Articles();

		Users user = userRepo.findByUsername(currentUser);

		if (user.getBookmarks() != null) {
			bookmarkList = user.getBookmarks();
			if (!bookmarkList.isEmpty()) {
				for (String article : bookmarkList) {
					Optional<Articles> optionalArticle = articleRepository.findById(article);
					if (optionalArticle.isPresent()) {
						articleInfo = optionalArticle.get();
						articles.add(new ArticlesDto(articleInfo.getId(), articleInfo.getPostTitle(),
								articleInfo.getPostDescription(), articleInfo.getTimestamp(), articleInfo.getUser()));
					}

				}
			} else {
				throw new MessageException("No added user bookmarks!!!");
			}

		} else {
			throw new MessageException("No added user bookmarks!!!");
		}

		return articles;
	}

	public boolean removeBookmark(@Valid IDRequestModel idRequestModel, String currentUser) throws MessageException {
		Articles article = new Articles();
		HashSet<String> bookmarkList = new HashSet<String>();

		Optional<Articles> optionalArticle = articleRepository.findById(idRequestModel.getId());

		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
			if (article.getBookmarkCount() > 0) {
				article.setBookmarkCount(article.getBookmarkCount() - 1);
				articleRepository.save(article);
			} else {
				throw new MessageException("No bookmarks for this article!!");

			}
		}

		Users user = userRepo.findByUsername(currentUser);

		if (user.getBookmarks() != null) {
			bookmarkList = user.getBookmarks();
			bookmarkList.remove(idRequestModel.getId());
			user.setBookmarks(bookmarkList);
			userRepo.save(user);
		} else {
			throw new MessageException("User not having any bookmarks!!!");

		}

		return true;

	}

	public boolean unlikeArticle(@Valid IDRequestModel idRequestModel, String currentUser) throws MessageException {
		Articles article = new Articles();
		HashSet<String> likesList = new HashSet<String>();

		Optional<Articles> optionalArticle = articleRepository.findById(idRequestModel.getId());

		if (optionalArticle.isPresent()) {
			article = optionalArticle.get();
			if (article.getLikes() > 0) {
				article.setLikes(article.getLikes() - 1);
				articleRepository.save(article);
			} else {
				throw new MessageException("No likes for this article!!");

			}
		}

		Users user = userRepo.findByUsername(currentUser);

		if (user.getLiked() != null) {
			likesList = user.getLiked();
			likesList.remove(idRequestModel.getId());
			user.setLiked(likesList);
			userRepo.save(user);
		} else {
			throw new MessageException("User not at liked any posts!!!");

		}

		return true;

	}

	public boolean notificationRead(IDRequestModel notificationId, String currentUser) throws MessageException {
		Users user = userRepo.findByUsername(currentUser);
		HashSet<NotificationsDto> notificationList = new HashSet<NotificationsDto>();
		boolean checkNotification = false;

		if (user.getNotifications() != null) {
			notificationList = user.getNotifications();
			for (NotificationsDto notification : notificationList) {
				if (notificationId.getId().equals(notification.getId())) {
					notification.setIsread(true);
					checkNotification = true;
				}
			}
			if (!checkNotification) {
				throw new MessageException("User is not having this notification!!");
			}
			user.setNotifications(notificationList);
			userRepo.save(user);
		} else {
			throw new MessageException("No notifications for the user!!!");

		}

		return true;

	}

}
