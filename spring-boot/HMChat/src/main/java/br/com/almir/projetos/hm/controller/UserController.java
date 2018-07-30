package br.com.almir.projetos.hm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.almir.projetos.hm.model.ChatMessage;
import br.com.almir.projetos.hm.model.User;
import br.com.almir.projetos.hm.repositories.ChatMessageRepository;
import br.com.almir.projetos.hm.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ChatMessageRepository chatMessageRepository;
	
	
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody User user) {
		user.setPassword(getEncoder().encode(user.getPassword()));		
		userRepository.save(user);

		if (user.getId() != null) {
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUsers() {
		List<User> users = userRepository.findAll();
		
		String loggedUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(User u: users) {
			if(loggedUser.equals(u.getUsername()))
				continue;
			
			List<ChatMessage> messages = new ArrayList<ChatMessage>();
			List<ChatMessage> messagesToUser = chatMessageRepository.findByFromUsernameAndToUsername(loggedUser, u.getUsername());
			List<ChatMessage> messagesFromUser = chatMessageRepository.findByFromUsernameAndToUsername(u.getUsername(), loggedUser);
			messages.addAll(messagesToUser);
			messages.addAll(messagesFromUser);
			u.setMessages(messages);
		}
		
		
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}


}
