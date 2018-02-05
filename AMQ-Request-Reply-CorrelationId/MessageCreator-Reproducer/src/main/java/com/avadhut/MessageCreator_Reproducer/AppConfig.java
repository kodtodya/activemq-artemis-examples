package com.avadhut.MessageCreator_Reproducer;


import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppConfig {

  public static void main(String[] args) throws InterruptedException {
     
	  try {
	  ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      MyMessageSender ms = context.getBean(MyMessageSender.class);
     
	  String response = ms.sendMessage("my-msg");
	
	  System.out.println("response is :"+response);
      
	  } catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}