package br.com.almir.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.almir.model.Permissao;
import br.com.almir.model.User;
import br.com.almir.service.SecurityService;
import br.com.almir.service.UserService;



@Controller
@RequestMapping("/user")
public class SecurityController {
	

        
	
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserService userService;
	
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	System.out.println("login");
    	try {    		
    		User user = securityService.authenticate(username, password);
			return new ResponseEntity<User>(user, HttpStatus.OK);	  
			
    	}catch(AuthenticationException e) {
    		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);	
    	}		    
    	
    	
    }
    
	@RequestMapping(method = RequestMethod.POST, path="register")
	public String register(User user, UriComponentsBuilder ucBuilder) {		

		if (userService.isUserExist(user)) {			
			return "redirect:/loginError";
		}
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));                
		userService.saveUser(user);

		return "redirect:/home";
	}

}
