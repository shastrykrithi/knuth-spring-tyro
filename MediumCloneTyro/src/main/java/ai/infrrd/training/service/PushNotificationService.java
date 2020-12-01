package ai.infrrd.training.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import ai.infrrd.training.exception.MessageException;
import ai.infrrd.training.model.Users;
import ai.infrrd.training.payload.request.IDRequestModel;
import ai.infrrd.training.payload.response.PushNotificationResponse;
import ai.infrrd.training.repository.UserRepository;


@Service
public class PushNotificationService {
	
	@Autowired
	UserRepository userRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    
	public String sendPushNotificationToDevice(PushNotificationResponse pushNotificationResponse) {
        Message message = Message.builder()
                .setToken(pushNotificationResponse.getTarget())
                .setNotification(new Notification(pushNotificationResponse.getTitle(), pushNotificationResponse.getBody()))
                .putData("content", pushNotificationResponse.getTitle())
                .putData("body", pushNotificationResponse.getBody())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Push Notififcation: "+response);
        } catch (FirebaseMessagingException e) {
        	logger.error("Fail to send firebase notification", e);
        }

        return response;
    }

	public boolean activateNotification(IDRequestModel deviceToken, String currentUser) throws MessageException{
		Users user = userRepo.findByUsername(currentUser);
		user.setDeviceToken(deviceToken.getId());
		userRepo.save(user);
		return true;

	}
	
	
}
