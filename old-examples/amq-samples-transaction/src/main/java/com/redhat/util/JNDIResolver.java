package com.redhat.util;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIResolver {

	
	public final static String JNDI_FACTORY="org.apache.activemq.jndi.ActiveMQInitialContextFactory";
    public final static String JMS_FACTORY="ConnectionFactory";
    public final static String QUEUE="dynamicQueues/MyQ";
    public final static String USER="admin";
    public final static String PASSWORD="admin";
    private static ConnectionFactory conFactory;
    private static Connection connection;
    private static Session session;
    
    
    public static InitialContext getInitialContext(String url) throws NamingException
    {
         Hashtable env = new Hashtable();
         env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
         env.put(Context.PROVIDER_URL, url);
         return new InitialContext(env);
    }
    
    public static Session init(String url) throws JMSException, NamingException{
    	conFactory = (ConnectionFactory) getInitialContext(url).lookup(JNDIResolver.JMS_FACTORY);
        connection = conFactory.createConnection(JNDIResolver.USER,JNDIResolver.PASSWORD);
        session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        //session = connection.createSession();
        return session;
    }

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		JNDIResolver.connection = connection;
	}

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		JNDIResolver.session = session;
	}

	public static void close() throws JMSException {
		session.close();
		connection.close();
		
	}
}
