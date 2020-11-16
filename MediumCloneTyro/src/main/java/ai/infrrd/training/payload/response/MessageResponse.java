package ai.infrrd.training.payload.response;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Response message for the request")
public class MessageResponse {
	
	private String message;
	private String result;

	public MessageResponse(String result,String message) {
	    this.message = message;
	    this.result=result;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
