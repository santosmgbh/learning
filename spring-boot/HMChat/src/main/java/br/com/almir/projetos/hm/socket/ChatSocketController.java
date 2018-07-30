package br.com.almir.projetos.hm.socket;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import br.com.almir.projetos.hm.model.ChatMessage;
import br.com.almir.projetos.hm.model.UpdatedMessagesInfo;
import br.com.almir.projetos.hm.model.User;
import br.com.almir.projetos.hm.repositories.ChatMessageRepository;
import br.com.almir.projetos.hm.repositories.UserRepository;

@Controller
public class ChatSocketController {

	@Autowired
	private SimpMessagingTemplate messageSender;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ChatMessageRepository chatMessageRepository;

	@MessageMapping("/sendMessage")
	@SendTo("/chat/messages/{to}")
	public void sendMessage(ChatMessage message) throws Exception {		
		message.setSended(new Date());
		chatMessageRepository.save(message);
		messageSender.convertAndSend("/chat/messages/"+message.getToUsername(), message);							
	}
	
	@MessageMapping("/checkReadMessages")
	@SendTo("/chat/messagesRead/{to}")
	public void checkMessage(UpdatedMessagesInfo updateMessages) throws Exception {		
		List<ChatMessage> messagesToUser = chatMessageRepository.findByFromUsernameAndToUsername(updateMessages.getFromUsername(), updateMessages.getToUsername());		
		for(ChatMessage m: messagesToUser){
			m.setRead(true);
			chatMessageRepository.save(m);
		}
		
		messageSender.convertAndSend("/chat/messagesRead/"+updateMessages.getFromUsername(), new UpdatedMessagesInfo(updateMessages.getFromUsername(), updateMessages.getToUsername()));							
	}
	
	@MessageMapping("/userConnected")
	@SendTo("/chat/userConnected")
	public void userConnected(User user) throws Exception {
		user = userRepository.findByUsername(user.getUsername());
		user.setConnected(true);
		userRepository.save(user);
		messageSender.convertAndSend("/chat/userConnected", user);							
	}
	
	@MessageMapping("/userDisconnected")
	@SendTo("/chat/userDisconnected")
	public void userDisconnected(User user) throws Exception {
		user = userRepository.findByUsername(user.getUsername());
		user.setConnected(false);
		userRepository.save(user);
		messageSender.convertAndSend("/chat/userDisconnected", user);							
	}
	

}
