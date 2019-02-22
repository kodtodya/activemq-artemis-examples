package com.redhat.activemq_jndi_sample;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIResolver {
	
	private final static String JNDI_FACTORY="org.apache.activemq.jndi.ActiveMQInitialContextFactory";
	
	public static InitialContext getInitialContext(String url) throws NamingException
    {
         Hashtable env = new Hashtable();
         env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
         env.put(Context.PROVIDER_URL, url);
         return new InitialContext(env);
    }

}
