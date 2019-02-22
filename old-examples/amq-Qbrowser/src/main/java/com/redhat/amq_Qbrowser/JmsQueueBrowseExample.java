package com.redhat.amq_Qbrowser;

import java.net.URISyntaxException;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsQueueBrowseExample {
	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		try {
			// Producer
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin",
					"tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("test_queue");
			MessageProducer producer = session.createProducer(queue);
			String task = "Task";
			for (int i = 0; i < 10; i++) {
				String payload = task + i;
				Message msg = session.createTextMessage(payload);
				if (i % 2 == 0) {
					msg.setStringProperty("sequence", "even");
				}
				System.out.println("Sending text '" + payload + "'");
				producer.send(msg);
			}

			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();

			System.out.println("Browse through the elements in queue");
			// QueueBrowser browser = session.createBrowser(queue);
			QueueBrowser browser = session.createBrowser(queue, "sequence = 'even'");
			Enumeration e = browser.getEnumeration();
			while (e.hasMoreElements()) {
				TextMessage message = (TextMessage) e.nextElement();
				System.out.println("Browse [" + message.getText() + "]");
			}
			System.out.println("Done");
			browser.close();

			for (int i = 0; i < 10; i++) {
				TextMessage textMsg = (TextMessage) consumer.receive();
				System.out.println(textMsg);
				System.out.println("Received: " + textMsg.getText());
			}
			session.close();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}