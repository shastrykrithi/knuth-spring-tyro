package ai.infrrd.training.model;

import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.infrrd.training.dto.TopicsDto;
import ai.infrrd.training.dto.UserDto;

@Document(collection = "users")
public class Users {

	@Id
	private String id;
	@Indexed(unique = true)
	private String username;
	private String password;
	@Indexed(unique = true)
	private String email;
	private HashSet<UserDto> following;
	private HashSet<String> publishedArticles;
	private HashSet<TopicsDto> topics;
	private HashSet<String> bookmarks;
	private HashSet<String> liked;

	public Users() {

	}

	public Users(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public HashSet<UserDto> getFollowing() {
		return following;
	}

	public void setFollowing(HashSet<UserDto> following) {
		this.following = following;
	}

	public HashSet<String> getArticles() {
		return publishedArticles;
	}

	public void setArticles(HashSet<String> publishedArticles) {
		this.publishedArticles = publishedArticles;
	}

	public HashSet<TopicsDto> getTopics() {
		return topics;
	}

	public void setTopics(HashSet<TopicsDto> topics) {
		this.topics = topics;
	}
	
	
	public HashSet<String> getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(HashSet<String> bookmarks) {
		this.bookmarks = bookmarks;
	}
	

	public HashSet<String> getLiked() {
		return liked;
	}

	public void setLiked(HashSet<String> liked) {
		this.liked = liked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((publishedArticles == null) ? 0 : publishedArticles.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((following == null) ? 0 : following.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Users other = (Users) obj;
		if (publishedArticles == null) {
			if (other.publishedArticles != null)
				return false;
		} else if (!publishedArticles.equals(other.publishedArticles))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (following == null) {
			if (other.following != null)
				return false;
		} else if (!following.equals(other.following))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if (!topics.equals(other.topics))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", following=" + following + ", topics=" + topics + "]";
	}

}