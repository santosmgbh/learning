package br.com.almir.projetos.hm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.almir.projetos.hm.model.ChatMessage;
import br.com.almir.projetos.hm.model.User;

public interface ChatMessageRepository  extends JpaRepository<ChatMessage, Long>  {
	

	List<ChatMessage> findByFromUsernameAndToUsername(String fromUsername, String toUsername);	
	         
       
}