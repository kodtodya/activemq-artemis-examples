package com.redhat.activemq.producer_property;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

public class SampleMessageProducerProperty {
	public static void main(String[] args) throws Exception {
		MessageProducer sender = null;
		MessageConsumer receiver = null;

		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","failover:(tcp://localhost:61616,tcp://localhost:61617)?timeout=5000");

		// create a connection
		Connection connection = connectionFactory.createConnection();

		// create a transacted session
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

		Destination queue = session.createQueue("testQueue");
		
		// create a (queue) message producer
		//sender = ((ActiveMQMessageProducer) session.createProducer(queue));
		
		ActiveMQMessageProducer activemqProducer= (ActiveMQMessageProducer) session.createProducer(queue);
		activemqProducer.setSendTimeout(3000);
		
		sender = activemqProducer;
		
		// create a (queue) message consumer
		receiver = session.createConsumer(queue);

		// start the connection
		connection.start();
System.out.println("connection started");
		// send a few messages to the queue
		int i=1;
		for(i=1;i<=5;i++){
		sender.send(session.createTextMessage(new String().valueOf(i)));
		System.out.println("msg#" + i + " sent to broker..");
		Thread.sleep(5000*i);
		}
		System.out.println(i-- +" msgs sent to queue");
		// commit the session (actually put messages on queue)
		session.commit();

		
		
		
		// consume messages from queue and produce to topic in one transaction
		TextMessage msg; 
		for(i=0;i<5;i++){
			System.out.println((TextMessage) receiver.receive());
		}
		
		System.out.println("\n>>total msgs received->" +i);

		// commit receive of topic messages
		session.commit();

		// close connection
		connection.close();
	}
}