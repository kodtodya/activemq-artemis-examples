package com.avadhut.amqp;

import javax.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.jms.core.*;

public class AMQPProducer {

	private static ConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:61616");
	private static JmsTemplate jmsTemplate = new JmsTemplate(factory);
	private static String requestQueueName = "myQueue";

	public static void main(final String[] args) throws Exception {

		try {
			String msg = "my test message";
			jmsTemplate.setTimeToLive(10000);
			jmsTemplate.setExplicitQosEnabled(true);
			jmsTemplate.setReceiveTimeout(100000);

			System.out.println("sending: \"" + msg + "\"");

			String correlationId = "sample-correlatoion-id";

			Message response = jmsTemplate.sendAndReceive(requestQueueName, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage();
					message.setText(msg);
					message.setJMSCorrelationID(correlationId);
					return message;
				}
			});

			String result = null;
			System.out.println(correlationId.equalsIgnoreCase(response.getJMSCorrelationID()) ? "\nCorrelation id is same after getting reply..": "\nCorrelation id is not same after getting reply..");
			result = ((TextMessage) response).getText();

			System.out.println("\nInside the send() method itself : response msg is : \"" + result +"\"");
			
		} catch (JMSException e) {
			System.out.println(e);
		}
	}
}