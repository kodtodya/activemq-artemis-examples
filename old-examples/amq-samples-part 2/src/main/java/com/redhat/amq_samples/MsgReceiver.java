package com.redhat.amq_samples;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.activemq.command.ActiveMQDestination;

import com.redhat.util.JNDIResolver;

public class MsgReceiver// implements MessageListener
   {
	private Session session;
    private MessageConsumer _consumer;
 
 /*   boolean transacted = false;
    int maxAttempts = 1;
    boolean started = false;
*/
    public void init(String url, String destination)throws JMSException, NamingException
     {
        session = JNDIResolver.init(url);
        JNDIResolver.getConnection().start();
        _consumer=session.createConsumer(ActiveMQDestination.createDestination(destination, (byte) 1));
        
     }

   public void receiveTextMessage() throws JMSException 
     {
        Message message=null;
        int count = 0;
        // If any message will not be received within 30 Seconds then receiver will quit
        while(   (message=this._consumer.receive(30000))       !=   null)
          {
              if(message instanceof javax.jms.TextMessage)
                {
                  System.out.println((count++)  +" Received TextMessage : "+((TextMessage)message).getText());         
                }
          }
     }

    public static void main(String ar[]) throws Exception
     {
    	MsgReceiver receiver = new MsgReceiver();
    	receiver.init("tcp://localhost:61616","queue://test_queue");
        receiver.receiveTextMessage();
        JNDIResolver.close();
     }
}
