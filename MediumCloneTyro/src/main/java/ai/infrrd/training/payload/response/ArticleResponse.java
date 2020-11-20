package ai.infrrd.training.payload.response;

import java.util.List;

import ai.infrrd.training.dto.ArticlesDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Response body with list of Articles")
public class ArticleResponse {
	@ApiModelProperty(notes = "List of Articles")
	private List<ArticlesDto> articleslist;

	public ArticleResponse() {

	}

	public ArticleResponse(List<ArticlesDto> articleslist) {
		super();
		this.articleslist = articleslist;
	}

	public List<ArticlesDto> getArticleslist() {
		return articleslist;
	}

	public void setArticleslist(List<ArticlesDto> articleslist) {
		this.articleslist = articleslist;
	}

}
