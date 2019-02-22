package com.kodtodya.practice;

import org.apache.activemq.artemis.api.core.ActiveMQInterruptedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringMessageConsumerApplication {

    public static void main(String[] args) throws ActiveMQInterruptedException {

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

            System.err.println("Artemis Consumer started...");

            MessageConsumer consumer = context.getBean(MessageConsumer.class);
            consumer.receiveMessage();

        } catch (Exception exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
    }
}