package br.com.almir.projetos.hm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import br.com.almir.projetos.hm.model.ChatMessage;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate messageSender;

	@MessageMapping("/sendMessage")
	@SendTo("/chat/messages/{to}")
	public void greeting(ChatMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		System.out.println("sendMEssage");
		System.out.println(message.getText());

		messageSender.convertAndSend("/chat/messages/"+message.getToUser(), message);		
	}

}