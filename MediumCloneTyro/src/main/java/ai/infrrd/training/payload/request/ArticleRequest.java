package ai.infrrd.training.payload.request;

import java.util.HashSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required for writing an Article")
public class ArticleRequest {

	@ApiModelProperty(notes = "The Title of the Article")
	@NotNull(message = "Post title can not be null")
	@Size(min = 4, message = "Post title allowed length is minimum 4")
	private String postTitle;
	@ApiModelProperty(notes = "Description of the Article")
	@NotNull(message = "Post description can not be empty")
	@Size(min = 10, message = "Post description allowed length is minimum 10")
	private String postDescription;
	@ApiModelProperty(notes = "Topics related to the Article")
	private HashSet<IDRequestModel> topics;

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

	public HashSet<IDRequestModel> getTopics() {
		return topics;
	}

	public void setTopics(HashSet<IDRequestModel> topics) {
		this.topics = topics;
	}

}
