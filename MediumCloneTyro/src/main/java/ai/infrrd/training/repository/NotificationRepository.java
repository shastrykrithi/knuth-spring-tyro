package ai.infrrd.training.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.model.Notifications;

@Repository

public interface NotificationRepository  extends MongoRepository<Notifications, Serializable> {
	
	Notifications findByNotifyforAndPostId(String notifyfor, String id); 
	
	Notifications findByNotifyforAndPostIdAndNotificationName(String notifyfor, String id, String NotificationName); 
	
	Notifications findByNotificationNameAndPostId(String notifyname, String id); 
	
	Notifications findByNotifyforAndNotificationName(String notifyname, String name); 
	

	boolean existsById(String id);

}
