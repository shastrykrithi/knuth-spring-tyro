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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notificationName == null) ? 0 : notificationName.hashCode());
		result = prime * result + ((notifyfor == null) ? 0 : notifyfor.hashCode());
		result = prime * result + ((postId == null) ? 0 : postId.hashCode());
		result = prime * result + ((postTitle == null) ? 0 : postTitle.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notifications other = (Notifications) obj;
		if (notificationName == null) {
			if (other.notificationName != null)
				return false;
		} else if (!notificationName.equals(other.notificationName))
			return false;
		if (notifyfor == null) {
			if (other.notifyfor != null)
				return false;
		} else if (!notifyfor.equals(other.notifyfor))
			return false;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (postTitle == null) {
			if (other.postTitle != null)
				return false;
		} else if (!postTitle.equals(other.postTitle))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}


	
	

}


