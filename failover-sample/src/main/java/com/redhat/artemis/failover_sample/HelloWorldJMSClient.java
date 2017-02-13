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
package com.redhat.artemis.failover_sample;

import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;

public class HelloWorldJMSClient {
    private static final Logger log = Logger.getLogger(HelloWorldJMSClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin";
    private static final String DEFAULT_CLIENTID = "myClient";
    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1,tcp://localhost:61617?ha=true&retryInterval=1000&retryIntervalMultiplier=1.0&reconnectAttempts=-1";
    
    public static Connection connect() throws JMSException {
        
        // AMQ6.3  
        // org.apache.activemq.ActiveMQConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory( p.getBrokerURL() );
        
    	// AMQ7
        org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory factory = new org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory( DEFAULT_BROKER_URL );

        Connection connection = factory.createConnection( DEFAULT_USERNAME, DEFAULT_PASSWORD);
        
        connection.setClientID( DEFAULT_CLIENTID );
        
        connection.start();
        
        return connection;
    }
    
    
    
    public static void main(String[] args) throws JMSException {

    	System.out.println("before creating connection");
    	Connection connection = connect();
    	
    	if (connection!=null)
    		log.info("Connection clientId: "+connection.getClientID());
    	else
    		log.info("connection is null");	
    		
    	
    	
    }
}