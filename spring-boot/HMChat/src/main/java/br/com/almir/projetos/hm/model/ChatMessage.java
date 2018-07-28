package br.com.almir.projetos.hm.model;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChatMessage() {
		
	}
	
	private String text;
	private String toUser;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	

	
}
