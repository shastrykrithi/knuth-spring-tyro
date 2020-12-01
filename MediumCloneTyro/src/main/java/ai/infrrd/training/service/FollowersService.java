package ai.infrrd.training.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.infrrd.training.dto.NotificationsDto;
import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Notifications;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.IDRequestModel;
import ai.infrrd.training.payload.response.PushNotificationResponse;
import ai.infrrd.training.repository.FollowerRepository;
import ai.infrrd.training.repository.NotificationRepository;
import ai.infrrd.training.repository.UserRepository;

@Service
public class FollowersService {

	@Autowired
	FollowerRepository followerRepository;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	PushNotificationService pushNotificationService;

	public HashSet<UserDto> getAllFollowers() throws MessageException {

		HashSet<UserDto> filteredfollowerList = new HashSet<UserDto>();
		List<Users> allfollowerList = followerRepository.findAll();
		if (!allfollowerList.isEmpty()) {
			for (Users element : allfollowerList) {
				UserDto userDto = new UserDto(element.getId(), element.getUsername());
				filteredfollowerList.add(userDto);
			}
		} else {
			throw new MessageException("User list is empty");
		}
		return filteredfollowerList;
	}

	public HashSet<UserDto> getUserFollowers(String username) throws MessageException {
		Users user = userRepo.findByUsername(username);
		HashSet<UserDto> allUsers = getAllFollowers();
		HashSet<UserDto> userFollowers = new HashSet<UserDto>();

		HashSet<UserDto> filteredFollowers = new HashSet<UserDto>();

		if (allUsers.isEmpty()) {
			throw new MessageException("No Users in DB!!!!");
		}

		if (user.getFollowing() != null) {
			userFollowers = user.getFollowing();
			for (UserDto element : allUsers) {
				if (user.getUsername().equals(element.getUsername())) {
					continue;
				}
				UserDto userdto = new UserDto(element.getId(), element.getUsername());
				if (userFollowers.contains(element)) {
					userdto.setIsfollowing(true);
					filteredFollowers.add(userdto);
				} else {
					userdto.setIsfollowing(false);
					filteredFollowers.add(userdto);
				}
			}

		} else {
			for (UserDto element : allUsers) {
				if (user.getUsername().equals(element.getUsername())) {
					continue;
				}
				UserDto userdto = new UserDto(element.getId(), element.getUsername());
				userdto.setIsfollowing(false);
				filteredFollowers.add(userdto);
			}
		}
		return filteredFollowers;

	}

	public boolean followUser(IDRequestModel followerRequest, String username) throws MessageException {

		Users user = userRepo.findByUsername(username);

		Optional<Users> optionalUser = userRepo.findById(followerRequest.getId());
		

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		HashSet<UserDto> newfollowedByList = new HashSet<UserDto>();
		HashSet<NotificationsDto> notificationList = new HashSet<NotificationsDto>();
		Users followUser = new Users();

		if (optionalUser.isPresent()) {
			followUser = optionalUser.get();

			if (user.getFollowing() != null) {
				newUserList = user.getFollowing();
			}
			
			if(followUser.getFollowedBy()!=null) {
				newfollowedByList=followUser.getFollowedBy();
			}
			
			
			newUserList.add(new UserDto(followUser.getId(), followUser.getUsername()));
			newfollowedByList.add(new UserDto(user.getId(), username));
			followUser.setFollowedBy(newfollowedByList);
			user.setFollowing(newUserList);
			userRepo.save(user);
			userRepo.save(followUser);
			
			// set notification
			Notifications notification = notificationRepository.findByNotifyforAndNotificationName("follow",
					username);
			
			if(notification==null) {
				notification = new Notifications("follow", username);
				notificationRepository.save(notification);
			}
			
				if (userRepo.existsByUsername(followUser.getUsername())) {
					if (followUser.getNotifications() != null) {
						notificationList = followUser.getNotifications();

					}

					Notifications currentNotification = notificationRepository.findByNotifyforAndNotificationName("follow",
									username);
					
					if (currentNotification.getId() != null) {
						notificationList.add(new NotificationsDto(currentNotification.getId(), false));
					}
					followUser.setNotifications(notificationList);
					userRepo.save(followUser);
					
					if (followUser.getDeviceToken() != null) {
						PushNotificationResponse pushNotificationResponse = new PushNotificationResponse();
						pushNotificationResponse.setTarget(user.getDeviceToken());
						pushNotificationResponse.setTitle("follow");
						pushNotificationResponse.setBody(currentNotification.toString());
						pushNotificationService.sendPushNotificationToDevice(pushNotificationResponse);
					}
				}
			
		}
		
		return true;
	}

	public boolean unfollowUser(IDRequestModel followerRequest, String username) throws MessageException {

		Users user = userRepo.findByUsername(username);

		Optional<Users> optionalUser = userRepo.findById(followerRequest.getId());

		HashSet<UserDto> newUserList = new HashSet<UserDto>();
		HashSet<UserDto> newfollowedByList = new HashSet<UserDto>();
		Users followUser = new Users();

		if (optionalUser.isPresent()) {
			followUser = optionalUser.get();

			if (user.getFollowing() != null ) {
				newUserList = user.getFollowing();
			}
			else {
				throw new MessageException("User not following anyone!!");
			}
			
			if(followUser.getFollowedBy()!=null) {
				newfollowedByList=followUser.getFollowedBy();
			}
			if(!newUserList.isEmpty()||!newfollowedByList.isEmpty()) {
				newUserList.remove(new UserDto(followUser.getId(), followUser.getUsername()));
				newfollowedByList.remove(new UserDto(user.getId(), username));
			}
			else {
				throw new MessageException("User not following anyone!!");
			}
			followUser.setFollowedBy(newfollowedByList);
			user.setFollowing(newUserList);
			userRepo.save(user);
			userRepo.save(followUser);
		}
		return true;
	}

}
