package com.avadhut.MessageCreator_Reproducer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MyMessageSender {

  private JmsTemplate jmsTemplate;
  private String requestQueueName;

public String getRequestQueueName() {
	return requestQueueName;
}


public void setRequestQueueName(String requestQueueName) {
	this.requestQueueName = requestQueueName;
}


public JmsTemplate getJmsTemplate() {
	return jmsTemplate;
}


public void setJmsTemplate(JmsTemplate jmsTemplate) {
	this.jmsTemplate = jmsTemplate;
}


public String sendMessage(final String msg) throws JMSException {
      System.out.println("sending: " + msg);
      
      String correlationId = "sample-correlatoion-id";
      
      //Setup a message producer to send message to the queue the server is consuming from
      Message response = jmsTemplate.sendAndReceive(requestQueueName,
                      new MessageCreator() {
                          public Message createMessage(Session session) throws JMSException {
                              TextMessage message = session.createTextMessage();
                              message.setText(msg);
                              message.setJMSCorrelationID(correlationId);
                              return message;
                          }
                      });

      String result = null;
      try {
    	  System.out.println(correlationId.equalsIgnoreCase(response.getJMSCorrelationID())?"Correlation id is same..":"Correlation id is not same..");
          result = ((TextMessage) response).getText();
          
          System.out.println("inside the send() method: response msg is :"+ result);
      } catch (JMSException e) {
          System.out.println(e);
      }
      return result;
  }
}
