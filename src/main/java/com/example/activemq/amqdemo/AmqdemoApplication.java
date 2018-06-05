package com.example.activemq.amqdemo;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Queue;
import java.util.Date;

@Controller
@SpringBootApplication
public class AmqdemoApplication {

	private static final String QUEUE = "hello.queue";

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;


	@Bean
	public Queue queue() {
		return new ActiveMQQueue(QUEUE);
	}

	@RequestMapping("/send")
	@ResponseBody
	public String send(){
		System.out.println("发送数据为:"+new Date());
		this.jmsMessagingTemplate.convertAndSend(this.queue,new Date());
		return "send success";
	}

	@JmsListener(destination = QUEUE)
	public void receiver(String text){
		System.out.println("接收数据为:"+text);
	}

	public static void main(String[] args) {
		SpringApplication.run(AmqdemoApplication.class, args);
	}
}
