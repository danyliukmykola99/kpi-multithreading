package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@EnableScheduling
@Controller
public class MessageController {

	@Autowired
	private SimpMessagingTemplate template;

	@Scheduled(fixedRate = 5000)
	public void message() {
		this.template.convertAndSend("/topic/messages", new Message("Current time is " + OffsetDateTime.now()));
	}

}
