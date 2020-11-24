package ai.infrrd.training.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.TopicsDto;
import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.BusinessException;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Topics;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.TopicFollowRequest;
import ai.infrrd.training.repository.TopicRepository;
import ai.infrrd.training.repository.UserRepository;

@Service
public class TopicsService {

	@Autowired
	TopicRepository topicRepository;

	@Autowired
	UserRepository userRepo;

	public HashSet<TopicsDto> getAllTopics() throws MessageException {

		HashSet<TopicsDto> filteredTopicsList = new HashSet<TopicsDto>();
		List<Topics> allTopicsList = topicRepository.findAll();
		if (!allTopicsList.isEmpty()) {
			for (Topics element : allTopicsList) {
				TopicsDto topicDto = new TopicsDto(element.getId(), element.getTopicName());
				filteredTopicsList.add(topicDto);
			}
		} else {
			throw new MessageException("Topics list is empty");
		}
		return filteredTopicsList;
	}

	public boolean followTopic(TopicFollowRequest topicFollowRequest, String username) throws MessageException {

		Users user = userRepo.findByUsername(username);

		Optional<Topics> optionalTopic = topicRepository.findById(topicFollowRequest.getTopicID());

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		Topics topic = new Topics();

		if (optionalTopic.isPresent()) {
			topic = optionalTopic.get();
			if(topic.getUsers()!=null)
			newUserList = topic.getUsers();
		}

		newUserList.add(new UserDto(user.getId(), user.getUsername()));
		topic.setUsers(newUserList);
		topicRepository.save(topic);

		HashSet<TopicsDto> newtopicList = new HashSet<TopicsDto>();

		if (user.getTopics() != null) {
			newtopicList = user.getTopics();
		}

		newtopicList.add(new TopicsDto(topic.getId(), topic.getTopicName()));
		user.setTopics(newtopicList);
		userRepo.save(user);

		return true;

	}

	public boolean unfollowTopic(TopicFollowRequest topicFollowRequest, String username) throws MessageException {

		Users user = userRepo.findByUsername(username);

		Optional<Topics> optionalTopic = topicRepository.findById(topicFollowRequest.getTopicID());

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		Topics topic = new Topics();

		if (optionalTopic.isPresent()) {
			topic = optionalTopic.get();
			newUserList = topic.getUsers();
		}

		if (topic.getUsers() != null) {
			newUserList = topic.getUsers();
			newUserList.remove(new UserDto(user.getId(), user.getUsername()));
			topic.setUsers(newUserList);
			topicRepository.save(topic);
		} else {
			new MessageException("No users following the topic yet!!");
		}

		HashSet<TopicsDto> newtopicList = new HashSet<TopicsDto>();

		if (user.getTopics() != null) {
			newtopicList = user.getTopics();
			newtopicList.remove(new TopicsDto(topic.getId(), topic.getTopicName()));
			user.setTopics(newtopicList);
			userRepo.save(user);
		} else {
			new MessageException("No topics followed by user yet!!");
		}

		return true;

	}

	public HashSet<TopicsDto> getStringMatchTopics(String stringMatch) throws MessageException {
		HashSet<Topics> topicsList = topicRepository.findByTopicNameStartsWithIgnoreCase(stringMatch);
		HashSet<TopicsDto> filteredTopicsList = new HashSet<TopicsDto>();
		if (!topicsList.isEmpty()) {
			for (Topics topic : topicsList) {
				System.out.println(topic);
				filteredTopicsList.add(new TopicsDto(topic.getId(), topic.getTopicName()));
			}
			return filteredTopicsList;
		} else {
			throw new MessageException("No matching topics found");
		}

	}

	public HashSet<TopicsDto> getUserTopics(String username) throws MessageException {
		Users user = userRepo.findByUsername(username);
		HashSet<TopicsDto> allTopics = getAllTopics();
		HashSet<TopicsDto> userTopics = new HashSet<TopicsDto>();

		HashSet<TopicsDto> filteredTopics = new HashSet<TopicsDto>();

		if (allTopics.isEmpty()) {
			throw new MessageException("No topics in DB!!!!");
		}

		if (user.getTopics() != null) {
			userTopics = user.getTopics();
			for (TopicsDto element : allTopics) {
				TopicsDto topic = new TopicsDto(element.getId(), element.getTopicName());
				if (userTopics.contains(element)) {
					topic.setFollowing(true);
					filteredTopics.add(topic);
				} else {
					topic.setFollowing(false);
					filteredTopics.add(topic);
				}

			}

		} else {
			for (TopicsDto element : allTopics) {
				TopicsDto topic = new TopicsDto(element.getId(), element.getTopicName(), false);
				filteredTopics.add(topic);

			}
		}
		return filteredTopics;

	}
}
