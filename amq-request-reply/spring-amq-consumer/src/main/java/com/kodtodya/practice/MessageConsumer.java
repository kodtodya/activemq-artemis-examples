package com.kodtodya.practice;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


public class MessageConsumer {

    private JmsTemplate jmsTemplate;
    private String requestQueueName;


    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }


    public String getRequestQueueName() {
        return requestQueueName;
    }


    public void setRequestQueueName(String requestQueueName) {
        this.requestQueueName = requestQueueName;
    }


    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void receiveMessage() {
        Message message = jmsTemplate.receive(requestQueueName);
        try {

            if (message instanceof TextMessage) {

                final TextMessage txtMessage = (TextMessage) message;

                System.out.println("message received.. initializing reply to send back to :" + txtMessage.getJMSReplyTo());

                jmsTemplate.send(txtMessage.getJMSReplyTo(), new MessageCreator() {
                    public Message createMessage(final Session session) throws JMSException {
                        Message message = null;
                        try {
                            message = session.createTextMessage("test reply");
                            System.out.println("message text:" + txtMessage.getText());
                            message.setJMSCorrelationID(txtMessage.getJMSCorrelationID());

                        } catch (Exception exception) {
                            System.err.println(exception);
                        }
                        return message;
                    }
                });
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}