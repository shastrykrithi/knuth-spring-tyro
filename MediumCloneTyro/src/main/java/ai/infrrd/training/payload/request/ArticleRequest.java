package ai.infrrd.training.payload.request;

import java.util.HashSet;

import ai.infrrd.training.dto.TopicsDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Parameters required for writing an Article")
public class ArticleRequest {

	@ApiModelProperty(notes = "The Title of the Article")
	private String postTitle;
	@ApiModelProperty(notes = "Description of the Article")
	private String postDescription;
	@ApiModelProperty(notes = "Topics related to the Article")
	private HashSet<TopicsDto> topics;

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

	public HashSet<TopicsDto> getTopics() {
		return topics;
	}

	public void setTopics(HashSet<TopicsDto> topics) {
		this.topics = topics;
	}

}
