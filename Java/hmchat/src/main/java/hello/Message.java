package hello;

import java.util.Date;

public class Message {
	
	private Date dtSend;
	private String text;
	private User user;
	
	public Date getDtSend() {
		return dtSend;
	}
	public void setDtSend(Date dtSend) {
		this.dtSend = dtSend;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
