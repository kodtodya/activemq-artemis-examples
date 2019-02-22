package com.redhat.activemq.failover;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Message;

// AQ Specific APIs
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;

public class AMQQueueReceiver
  {
     Connection defaultConnection;
     Session defaultSession;
     MessageConsumer consumer;
     boolean transacted = false;
     int maxAttempts = 1;
     boolean started = false;
     private ActiveMQConnectionFactory connectionFactory;

     public AMQQueueReceiver(String user, String password, String brokerUrl,String destination)throws JMSException
      {
         this.connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
         System.out.println("Got Connection Factory (ActiveMQConnectionFactory): "+connectionFactory);
         this.defaultConnection = this.connectionFactory.createConnection();
         System.out.println("Got JMS Connection : "+defaultConnection);
         defaultConnection.start();
         this.defaultSession = this.defaultConnection.createSession(this.transacted, this.transacted ? 0 : 1);
         System.out.println("Got JMS Session : "+defaultSession);
         this.setMessageConsumer(destination);
      }

     public TextMessage createTestMessage(String text)throws JMSException
      {
         return this.defaultSession.createTextMessage(text);
      }

     public BytesMessage createBytesMessage(byte[] payload) throws JMSException 
      {
         BytesMessage message= this.defaultSession.createBytesMessage();
         message.writeBytes(payload);
         return message;
      }

     public MessageConsumer createConsumer(String destination) throws JMSException 
      {
         byte b=1;
         ActiveMQDestination queue = ActiveMQDestination.createDestination(destination, b);         
         return this.defaultSession.createConsumer(queue);
      }

     public MessageConsumer setMessageConsumer(String destination) throws JMSException 
      {
        consumer = createConsumer(destination);
        System.out.println("Got MessageConsumer : "+consumer);
        return consumer;
      }

     public void receiveTextMessage() throws JMSException 
      {
         Message message=null;
         int count = 0;
         // If any message will not be received within 30 Seconds then receiver will quit
         while(   (message=this.consumer.receive(30000))       !=   null)
           {
               if(message instanceof javax.jms.TextMessage)
                 {
                   System.out.println((count++)  +" Received TextMessage : "+((TextMessage)message).getText());         
                 }
           }
      }

     public static void main(String ar[]) throws Exception
      {
         AMQQueueReceiver senderAQ=new AMQQueueReceiver("admin","admin","failover:(tcp://localhost:61616,tcp://localhost:61616)","queue://MyQ");
         senderAQ.receiveTextMessage();
         senderAQ.defaultConnection.close();
      }
  }