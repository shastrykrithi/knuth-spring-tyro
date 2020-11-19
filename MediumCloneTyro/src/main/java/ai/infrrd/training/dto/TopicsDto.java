package ai.infrrd.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TopicsDto {
	
	private String id;
	private String topicName;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean following;
	
	public TopicsDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TopicsDto(String id, String topicName) {
		super();
		this.id = id;
		this.topicName = topicName;
	}
	
	
	public TopicsDto(String id, String topicName, boolean isFollowing) {
		super();
		this.id = id;
		this.topicName = topicName;
		this.following = isFollowing;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	

	public Boolean isFollowing() {
		return following;
	}
	
	public void setFollowing(Boolean following) {
		this.following = following;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((topicName == null) ? 0 : topicName.hashCode());
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
		TopicsDto other = (TopicsDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (topicName == null) {
			if (other.topicName != null)
				return false;
		} else if (!topicName.equals(other.topicName))
			return false;
		return true;
	}

	
	

}
