package ai.infrrd.training.model;

import java.util.HashSet;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.infrrd.training.dto.UserDto;
import ai.infrrd.training.payload.request.TopicFollowRequest;

@Document
public class Articles {
	@Id
	private String id;
	@NotNull
	private UserDto user;
	private String postTitle;
	private String postDescription;
	private long timestamp;
	private int views;
	private HashSet<TopicFollowRequest> topics;

	public Articles() {

	}

	public Articles(UserDto user, String postTitle, String postDescription, long timestamp,
			HashSet<TopicFollowRequest> topics) {
		super();
		this.user = user;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.topics = topics;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public HashSet<TopicFollowRequest> getTopics() {
		return topics;
	}

	public void setTopics(HashSet<TopicFollowRequest> topicsList) {
		this.topics = topicsList;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((postDescription == null) ? 0 : postDescription.hashCode());
		result = prime * result + ((postTitle == null) ? 0 : postTitle.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + views;
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
		Articles other = (Articles) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (postDescription == null) {
			if (other.postDescription != null)
				return false;
		} else if (!postDescription.equals(other.postDescription))
			return false;
		if (postTitle == null) {
			if (other.postTitle != null)
				return false;
		} else if (!postTitle.equals(other.postTitle))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if (!topics.equals(other.topics))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (views != other.views)
			return false;
		return true;
	}

}
