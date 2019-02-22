/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kodtodya.practice.examples;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import java.util.logging.Logger;

import javax.jms.*;

public class JMS2ArtemisFailoverExample {
    private static final Logger log = Logger.getLogger(JMS2ArtemisFailoverExample.class.getName());

    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1,tcp://localhost:61617?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1";


    public static void main(String[] args) throws JMSException {

        // AMQ7
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( DEFAULT_BROKER_URL );

        JMSContext context = factory.createContext();

        Destination destination = context.createQueue("myQueue");

        JMSProducer producer = context.createProducer();

        producer.send(destination,"sample message from Artemis Failover client...");

        System.out.println("Message sent..");


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JMSConsumer consumer = context.createConsumer(destination);

        Message message = consumer.receive();

        System.err.println("Message received : >>"+ message.getBody(String.class));

        context.close();
    }
}

