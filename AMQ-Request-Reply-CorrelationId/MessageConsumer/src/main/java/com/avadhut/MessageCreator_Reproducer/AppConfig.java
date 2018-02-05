package com.avadhut.MessageCreator_Reproducer;


import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppConfig {

  public static void main(String[] args) throws InterruptedException {
     
	  try {
	  ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

      MyMessageReceiver mr = context.getBean(MyMessageReceiver.class);
      mr.receiveMessage();
      
	  } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}