package ai.infrrd.training.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ai.infrrd.training.model.Notifications;
import ai.infrrd.training.model.Topics;

@Repository
public interface NotificationRepository  extends MongoRepository<Notifications, Serializable> {
	
	Notifications findByNotifyforAndPostId(String notifyfor, String id); 
	
	Notifications findByNotifyforAndPostIdAndNotificationName(String notifyfor, String id, String NotificationName); 
	
	Notifications findByNotificationNameAndPostId(String notifyname, String id); 
	
	boolean existsById(String id);
	

}
