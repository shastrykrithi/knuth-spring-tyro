package ai.infrrd.training.payload.request;

import java.util.Date;
import java.util.List;

import ai.infrrd.training.dto.TopicsDto;
import ai.infrrd.training.model.Topics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required for writing an Article")
public class ArticleRequest {

	@ApiModelProperty(notes = "The Username of the User")
	private String username;
	@ApiModelProperty(notes = "The Title of the Article")
	private String postTitle;
	@ApiModelProperty(notes = "Description of the Article")
	private String postDescription;
	@ApiModelProperty(notes = "Date and Time of Post")
	private Date timestamp;
	@ApiModelProperty(notes = "Topics related to the Article")
	private List<Topics> topics;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
