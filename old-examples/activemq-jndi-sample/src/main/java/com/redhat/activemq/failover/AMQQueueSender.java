package com.redhat.activemq.failover;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

// AQ Specific APIs
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;

public class AMQQueueSender
  {
     Connection defaultConnection;
     Session defaultSession;
     MessageProducer producer;
     boolean transacted = false;
     int maxAttempts = 1;
     boolean started = false;
     private ActiveMQConnectionFactory connectionFactory;

     public AMQQueueSender(String user, String password, String brokerUrl,String destination)throws JMSException
      {
         this.connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
         System.out.println("Got Connection Factory (ActiveMQConnectionFactory): "+connectionFactory);
         this.defaultConnection = this.connectionFactory.createConnection();
         System.out.println("Got JMS Connection : "+defaultConnection);
         this.defaultSession = this.defaultConnection.createSession(this.transacted, this.transacted ? 0 : 1);
         System.out.println("Got JMS Session : "+defaultSession);
         this.setMessageProducer(destination);
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

     public MessageProducer createProducer(String destination) throws JMSException 
      {
         byte b=1;
         ActiveMQDestination queue = ActiveMQDestination.createDestination(destination, b);         
         return this.defaultSession.createProducer(queue);
      }

     public MessageProducer setMessageProducer(String destination) throws JMSException 
      {
        producer = createProducer(destination);
        System.out.println("Got MessageProducer : "+producer);
        return producer;
      }

     public void sendTestMessage(String txt) throws JMSException 
      {
         TextMessage message=this.createTestMessage(txt);
         System.out.println("Created TextMessage : "+message);         
         this.producer.send(message);
         System.out.println("TextMessage sent> "+message);         
      }

     public static void main(String ar[]) throws Exception
      {
         AMQQueueSender senderAQ=new AMQQueueSender("admin","admin","failover:(tcp://localhost:61616,tcp://localhost:61616)","queue://MyQ");
         senderAQ.sendTestMessage("Message - "+"Hello One !!!");
         senderAQ.sendTestMessage("Message - "+"Hello Two !!!");
         senderAQ.sendTestMessage("Message - "+"Hello Three !!!");
         senderAQ.defaultConnection.close();
      }
  }