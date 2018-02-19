package com.avadhut.amqp;

import javax.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.jms.core.*;

public class AMQPConsumer {

	private static ConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:61616");
	private static JmsTemplate jmsTemplate = new JmsTemplate(factory);
	private static String requestQueueName = "myQueue";
	
	public static void main(final String[] args) throws Exception {
		try {

			jmsTemplate.setTimeToLive(10000);
			jmsTemplate.setExplicitQosEnabled(true);
			jmsTemplate.setReceiveTimeout(100000);

			Message message = jmsTemplate.receive(requestQueueName);

			if (message instanceof TextMessage) {
				
				final TextMessage txtMessage = (TextMessage) message;
				
				System.out.println("message received as : \"" + txtMessage.getText() + "\" and correlation Id is: \"" + txtMessage.getJMSCorrelationID() + "\"");
				System.out.println("\nInitializing reply to send back to : \"" + txtMessage.getJMSReplyTo() + "\"");
				
				jmsTemplate.send(txtMessage.getJMSReplyTo(), new MessageCreator() {
					public Message createMessage(final Session session) throws JMSException {
						Message message = null;
						try {
							String response = "test reply";
							System.out.println("\nSetting reply as : \""+ response + "\"");
							message = session.createTextMessage(response);
							message.setJMSCorrelationID(txtMessage.getJMSCorrelationID());

						} catch (Exception ex) {
							System.err.println(ex);
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