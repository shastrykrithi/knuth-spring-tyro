package ai.infrrd.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class NotificationsDto {

	private String id;
	private Boolean isread;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String notifyfor;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String notificationName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String postId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String postTitle;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long timestamp;

	public NotificationsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotificationsDto(String id, Boolean isread) {
		super();
		this.id = id;
		this.isread = isread;
	}
	
	

	public NotificationsDto(String id, Boolean isread, String notifyfor, String notificationName, String postId,
			String postTitle, Long timestamp) {
		super();
		this.id = id;
		this.isread = isread;
		this.notifyfor = notifyfor;
		this.notificationName = notificationName;
		this.postId = postId;
		this.postTitle = postTitle;
		this.timestamp = timestamp;
	}
	
	

	public NotificationsDto(String id, Boolean isread, String notifyfor, String notificationName) {
		super();
		this.id = id;
		this.isread = isread;
		this.notifyfor = notifyfor;
		this.notificationName = notificationName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsread() {
		return isread;
	}

	public void setIsread(Boolean isread) {
		this.isread = isread;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isread == null) ? 0 : isread.hashCode());
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
		NotificationsDto other = (NotificationsDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isread == null) {
			if (other.isread != null)
				return false;
		} else if (!isread.equals(other.isread))
			return false;
		return true;
	}
	
	

}
