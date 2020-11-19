package ai.infrrd.training.dto;

import java.util.Date;

import ai.infrrd.training.model.Users;

public class ArticlesDto {

	private String id;
	private Users user;
	private String postTitle;
	private String postDescription;
	private Date timestamp;
	private int views;

	public ArticlesDto() {
		// TODO Auto-generated constructor stub
	}

	public ArticlesDto(Users user, String postTitle, String postDescription, Date timestamp) {
		super();
		this.user = user;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
	}

	public ArticlesDto(Users user, String postTitle, Date timestamp) {
		this.user = user;
		this.postTitle = postTitle;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimeStamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	@Override
	public String toString() {
		return "ArticlesDto [user=" + user + ", postTitle=" + postTitle + ", postDescription=" + postDescription
				+ ", timestamp=" + timestamp + " ]";
	}

}
