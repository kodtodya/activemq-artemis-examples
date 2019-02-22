package com.redhat.activemq_jndi_sample;

import java.io.*;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Producer
      {
         //public final static String JNDI_FACTORY="org.apache.activemq.jndi.ActiveMQInitialContextFactory";
         public final static String JMS_FACTORY="ConnectionFactory";
         public final static String QUEUE="dynamicQueues/MyQ";
         public final static String USER="admin";
         public final static String PASSWORD="admin";

         private QueueConnectionFactory qconFactory;
         private QueueConnection qcon;
         private QueueSession qsession;
         private QueueSender qsender;
         private Queue queue;
         private TextMessage msg;

         public void init(Context ctx, String queueName)throws NamingException, JMSException
           {
               qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
               qcon = qconFactory.createQueueConnection(USER,PASSWORD);
               qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
               queue = (Queue) ctx.lookup(queueName);
               qsender = qsession.createSender(queue);
               msg = qsession.createTextMessage();
               qcon.start();
           }

         public void send(String message) throws JMSException 
           {
                      msg.setText(message);
                      qsender.send(msg);
           }

         public void close() throws JMSException 
           {
               qsender.close();
               qsession.close();
               qcon.close();
           }

         public static void main(String[] args) throws Exception 
           {
               InitialContext ic = JNDIResolver.getInitialContext("tcp://localhost:61616");//args[0]);
               Producer qs = new Producer();
               qs.init(ic, QUEUE);
               readAndSend(qs);
               qs.close();
           }

         private static void readAndSend(Producer qs) throws IOException, JMSException
           {
                //String line="Test Message Body with counter = ";
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                boolean readFlag=true;
                System.out.print("\n\tStart Sending Messages (Enter QUIT to Stop):\n ");
                while(readFlag)
                    {
                        System.out.print("<Msg_Sender> ");
                        String msg=br.readLine();
                        if(msg.equals("QUIT") || msg.equals("quit"))
                             {
                                 qs.send(msg);
                                 System.exit(0);
                             }
                        qs.send(msg);
                        System.out.println();
                    }
                br.close();
            }
 }