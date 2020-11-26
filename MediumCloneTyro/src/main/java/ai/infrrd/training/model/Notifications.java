package ai.infrrd.training.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Notifications {
	
	@Id
	private String id;
	private String notifyfor;
	private String notificationName;
	private String postId;
	private String postTitle;
	private Long timestamp;
	
	
	public Notifications() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Notifications(String notifyfor, String notificationName, String postId, String postTitle, Long timestamp) {
		super();
		this.notifyfor = notifyfor;
		this.notificationName = notificationName;
		this.postId = postId;
		this.postTitle = postTitle;
		this.timestamp = timestamp;
		
	}


	public Notifications( String notifyfor, String notificationName) {
		super();
		this.notifyfor = notifyfor;
		this.notificationName = notificationName;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getNotifyfor() {
		return notifyfor;
	}


	public void setNotifyfor(String notifyfor) {
		this.notifyfor = notifyfor;
	}


	public String getNotificationName() {
		return notificationName;
	}


	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}


	public String getPostId() {
		return postId;
	}


	public void setPostId(String postId) {
		this.postId = postId;
	}


	public String getPostTitle() {
		return postTitle;
	}


	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}


	public Long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}


	

}


