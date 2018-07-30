package br.com.almir.projetos.hm.model;

public class UpdatedMessagesInfo {
	
	private String fromUsername;
	private String toUsername;
	
	
	
	public UpdatedMessagesInfo(String fromUsername, String toUsername) {
		super();
		this.fromUsername = fromUsername;
		this.toUsername = toUsername;
	}
	
	
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	
	

}
