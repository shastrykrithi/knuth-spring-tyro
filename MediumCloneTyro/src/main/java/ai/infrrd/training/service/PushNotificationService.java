package ai.infrrd.training.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import ai.infrrd.training.dto.NotificationsDto;
import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Notifications;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.IDRequestModel;
import ai.infrrd.training.payload.response.PushNotificationResponse;
import ai.infrrd.training.repository.NotificationRepository;
import ai.infrrd.training.repository.UserRepository;

@Service
public class PushNotificationService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	NotificationRepository notificationRepository;

	private static final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

	public String sendPushNotificationToDevice(PushNotificationResponse pushNotificationResponse,
			Notifications notification) {
		
		Map<String, String> notificationData=new HashMap<String, String>();
		if (notification.getNotifyfor().equalsIgnoreCase("follow")) {
			notificationData.put("id", notification.getId());
			notificationData.put("notifyfor", notification.getNotifyfor());
			notificationData.put("notificationName", notification.getNotificationName());
			
		} else {
			notificationData.put("id", notification.getId());
			notificationData.put("notifyfor", notification.getNotifyfor());
			notificationData.put("notificationName", notification.getNotificationName());
			notificationData.put("postId", notification.getPostId());
			notificationData.put("postTitle", notification.getPostTitle());
			notificationData.put("timestamp", notification.getTimestamp().toString());

		}
		Message message = Message.builder().setToken(pushNotificationResponse.getTarget())
				.setNotification(
						new Notification(pushNotificationResponse.getTitle(), pushNotificationResponse.getBody()))
				.putAllData(notificationData).build();
		String response = null;
		try {
			response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Push Notififcation: " + response);
		} catch (FirebaseMessagingException e) {
			System.out.println("error:" + e.getMessage());
			logger.error("Fail to send firebase notification", e);
		}

		return response;
	}

	public boolean activateNotification(IDRequestModel deviceToken, String currentUser) throws MessageException {
		Users user = userRepo.findByUsername(currentUser);
		user.setDeviceToken(deviceToken.getId());
		userRepo.save(user);
		return true;

	}

	public boolean deActivateNotification(String currentUser) throws MessageException {
		Users user = userRepo.findByUsername(currentUser);
		user.setDeviceToken(null);
		userRepo.save(user);
		return true;

	}

	public List<NotificationsDto> getAllNotifications(String currentUser) throws MessageException {
		Users user = userRepo.findByUsername(currentUser);
		List<NotificationsDto> notificationList = new ArrayList<NotificationsDto>();
		if (user.getNotifications() != null) {
			HashSet<NotificationsDto> userNotifications = user.getNotifications();
			for (NotificationsDto notification : userNotifications) {
				Notifications currentNotification = notificationRepository.findById(notification.getId());
				if (currentNotification != null) {
					if (currentNotification.getNotifyfor().equalsIgnoreCase("follow")) {
						NotificationsDto addNotification = new NotificationsDto(currentNotification.getId(),
								notification.getIsread(), currentNotification.getNotifyfor(),
								currentNotification.getNotificationName());
						notificationList.add(addNotification);
					} else {
						NotificationsDto addNotification = new NotificationsDto(currentNotification.getId(),
								notification.getIsread(), currentNotification.getNotifyfor(),
								currentNotification.getNotificationName(), currentNotification.getPostId(),
								currentNotification.getPostTitle(), currentNotification.getTimestamp());
						notificationList.add(addNotification);
					}

				}

			}
		}
		else{
			throw new MessageException("No notifications for user!!!");
		}
		if(notificationList.isEmpty()) {
			throw new MessageException("No notifications for user!!!");
		}

		return notificationList;
	}

}
