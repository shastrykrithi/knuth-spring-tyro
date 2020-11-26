package ai.infrrd.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ArticlesDto implements Comparable<ArticlesDto> {

	private String id;
	private String postTitle;
	private String postDescription;
	private long timestamp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer views;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer likes;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer bookmarks;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean isBookmarked;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean isLiked;
	private UserDto user;

	public ArticlesDto() {

	}

	public ArticlesDto(String postTitle, String postDescription, long timestamp) {
		super();
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
	}

	public ArticlesDto(String id, String postTitle, String postDescription, long timestamp, int views, UserDto user) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.views = views;
		this.user = user;
	}
	
	

	public ArticlesDto(String id, String postTitle, String postDescription, long timestamp, UserDto user) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.user = user;
	}
	
	

	public ArticlesDto(String id, String postTitle, String postDescription, long timestamp, Integer views,
			Boolean isBookmarked, Boolean isLiked, UserDto user) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.views = views;
		this.isBookmarked = isBookmarked;
		this.isLiked = isLiked;
		this.user = user;
	}
	
	

	public ArticlesDto(String id, String postTitle, String postDescription, long timestamp, Integer views,
			Integer likes, Boolean isBookmarked, Boolean isLiked, UserDto user) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.views = views;
		this.likes = likes;
		this.isBookmarked = isBookmarked;
		this.isLiked = isLiked;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	
	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getBookmarks() {
		return bookmarks;
	}

	public void setBookmarks(Integer bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Boolean getIsBookmarked() {
		return isBookmarked;
	}

	public void setIsBookmarked(Boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	@Override
	public String toString() {
		return "ArticlesDto [id=" + id + ", postTitle=" + postTitle + ", postDescription=" + postDescription
				+ ", timestamp=" + timestamp + ", views=" + views + "]";
	}

	@Override
	public int compareTo(ArticlesDto compareTime) {
		int compareage = (int) ((ArticlesDto) compareTime).getTimestamp();
		return (int) (compareage - this.timestamp);
	}

}
