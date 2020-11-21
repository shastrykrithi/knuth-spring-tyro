package ai.infrrd.training.model;

import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.infrrd.training.dto.ArticlesDto;
import ai.infrrd.training.dto.UserDto;

@Document
public class Topics {

	@Id
	private String id;
	@Indexed(unique = true)
	private String topicName;
	private HashSet<UserDto> users;
	private HashSet<ArticlesDto> articles;

	public Topics() {
		
	}

	public Topics(String topicName) {
		super();
		// this.id = id;
		this.topicName = topicName;
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

	public HashSet<UserDto> getUsers() {
		return users;
	}

	public void setUsers(HashSet<UserDto> users) {
		this.users = users;
	}

	public HashSet<ArticlesDto> getArticles() {
		return articles;
	}

	public void setArticles(HashSet<ArticlesDto> articles) {
		this.articles = articles;
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
		Topics other = (Topics) obj;
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

	@Override
	public String toString() {
		return "Topics [id=" + id + ", topicName=" + topicName + "]";
	}

}
