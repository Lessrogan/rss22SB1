package fr.univrouen.rss22.client;

public class HTTPResponse {

	private int status;
	private String content;
	
	public HTTPResponse(int s, String c) {
		status = s;
		content = c;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int s) {
		status = s;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String c) {
		content = c;
	}
	
}
