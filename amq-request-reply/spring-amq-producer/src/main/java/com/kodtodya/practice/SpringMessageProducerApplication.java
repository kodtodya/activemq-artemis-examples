package com.kodtodya.practice;


import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringMessageProducerApplication {

    public static void main(String[] args) throws InterruptedException {

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

            System.err.println("Spring Message Producer started...");

            MessageProducer producer = context.getBean(MessageProducer.class);

            String response = producer.sendMessage("my-msg");

            System.out.println("response is :" + response);

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}