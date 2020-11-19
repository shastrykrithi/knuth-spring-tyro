package ai.infrrd.training.model;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.mongodb.lang.Nullable;

@Document
public class Articles {
	@Id
	private String id;
	@Indexed(unique = true)
	private String postId;
	@DBRef
	private Users user;
	@Nullable
	private String postTitle;
	@Nullable
	private String postDescription;
	private Date timestamp;
	@DBRef(lazy = true)
	private List<Topics> topics;
	public Articles() {
		
	}
	
	public Articles(String postId, Users user, String postTitle, String postDescription, Date timestamp,
			List<Topics> topics) {
		super();
		this.postId = postId;
		this.user = user;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.timestamp = timestamp;
		this.topics = topics;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
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

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<Topics> getTopics() {
		return topics;
	}
	public void setTopics(List<Topics> topics) {
		this.topics = topics;
	}
}
