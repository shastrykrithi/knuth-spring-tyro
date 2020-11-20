package ai.infrrd.training.dto;

public class ArticlesDto implements Comparable<ArticlesDto>{

	private String id;
	private String postTitle;
	private String postDescription;
	private long timestamp;
	private int views;
	private UserDto user;

	public ArticlesDto() {
		// TODO Auto-generated constructor stub
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

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
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

	@Override
	public String toString() {
		return "ArticlesDto [id=" + id + ", postTitle=" + postTitle + ", postDescription=" + postDescription
				+ ", timestamp=" + timestamp + ", views=" + views + "]";
	}

	@Override
	public int compareTo(ArticlesDto compareTime) {
        int compareage=(int) ((ArticlesDto)compareTime).getTimestamp();
//        /* For Ascending order*/
//        return this.studentage-compareage;

        return (int) (compareage-this.timestamp);
    }
	
	

	

	
	
	

	
}
