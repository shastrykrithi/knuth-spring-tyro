package ai.infrrd.training.payload.response;

public class PushNotificationResponse {
	private String target;
    private String title;
    private String body;
    
    
	public PushNotificationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
    
    
}