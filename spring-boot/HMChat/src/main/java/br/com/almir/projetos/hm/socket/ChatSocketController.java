package br.com.almir.projetos.hm.socket;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.almir.projetos.hm.model.ChatMessage;
import br.com.almir.projetos.hm.repositories.ChatMessageRepository;

@Controller
public class ChatSocketController {

	@Autowired
	private SimpMessagingTemplate messageSender;
	
	@Autowired
	ChatMessageRepository chatMessageRepository;

	@MessageMapping("/sendMessage")
	@SendTo("/chat/messages/{to}")
	public void sendMessage(ChatMessage message) throws Exception {		
		message.setSended(new Date());		
		messageSender.convertAndSend("/chat/messages/"+message.getToUsername(), message);	
		
		chatMessageRepository.save(message);
		
	}
	

}
