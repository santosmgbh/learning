package hello;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {


    @MessageMapping("/sendMessage")
    @SendTo("/messages")
    public Message sendMessage(Message message) throws Exception {        
    	message.setDtSend(new Date());        
    	message.setUser(new User());
        return message;
    }

}
