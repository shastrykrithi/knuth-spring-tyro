package ai.infrrd.training.payload.request;

import java.util.HashSet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required for writing an Article")
public class ArticleRequest {

	@ApiModelProperty(notes = "The Title of the Article")
	@NotEmpty(message = "Post title can not be null")
	@Size(min = 5, message = "Allowed length is minimum 5")
	private String postTitle;
	@ApiModelProperty(notes = "Description of the Article")
	@NotNull(message = "Post description can not be empty")
	@Size(min = 50, message = "Allowed length is minimum 50")
	private String postDescription;
	@ApiModelProperty(notes = "Topics related to the Article")
	private HashSet<TopicFollowRequest> topics;

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

	public HashSet<TopicFollowRequest> getTopics() {
		return topics;
	}

	public void setTopics(HashSet<TopicFollowRequest> topics) {
		this.topics = topics;
	}

}