package br.com.almir.service;


import java.util.List;

import org.springframework.security.core.AuthenticationException;

import br.com.almir.model.User;


public interface SecurityService {
	
	
	public User authenticate(String username, String password) throws AuthenticationException;

	public boolean validateKey(String key);
	
	public User getUser();
	
}