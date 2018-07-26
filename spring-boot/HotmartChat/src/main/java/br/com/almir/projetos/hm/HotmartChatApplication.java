package br.com.almir.projetos.hm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class HotmartChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotmartChatApplication.class, args);
	}

}
