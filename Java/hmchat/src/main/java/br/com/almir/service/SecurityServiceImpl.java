package br.com.almir.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.almir.model.User;
import br.com.almir.repositories.UserRepository;
import br.com.almir.util.WsUtil;




@Service("securityService")
@Transactional
public class SecurityServiceImpl implements SecurityService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	UserService userService;
	    
	private AuthenticationManager authenticationManager;
           
    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager;
    }

	@Override
	public User authenticate(String username, String password) throws AuthenticationException {     	
    	Authentication auth;
		try {
			auth = customAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
			if(auth.isAuthenticated()) {
	    		SecurityContextHolder.getContext().setAuthentication(auth);
	    		User user = userService.findByLogin(username);    		
	    		user.setKeyAuthentication(WsUtil.generateAuthKey(username, password));
	    		return user;
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
		return null;
	}

	@Override
	public boolean validateKey(String key) {
		User user = WsUtil.parseKey(key);	
		user = authenticate(user.getLogin(), user.getSenha());
		return user != null;
	}

	@Override
	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if(auth.isAuthenticated()) {
			user = userService.findByLogin(auth.getName());
		}
		
		return user;
	}	
		
	
}
