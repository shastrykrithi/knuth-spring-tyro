package ai.infrrd.training.payload.response;

import java.util.List;

import ai.infrrd.training.dto.ArticlesDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response body with list of Articles")
public class ArticleResponse {
	@ApiModelProperty(notes = "List of Articles")
	private List<ArticlesDto> articlesDto;

	public ArticleResponse() {

	}

	public ArticleResponse(List<ArticlesDto> articlesDto) {
		super();
		this.articlesDto = articlesDto;
	}

	public List<ArticlesDto> getArticlesDto() {
		return articlesDto;
	}

	public void setArticlesDto(List<ArticlesDto> articlesDto) {
		this.articlesDto = articlesDto;
	}

}
