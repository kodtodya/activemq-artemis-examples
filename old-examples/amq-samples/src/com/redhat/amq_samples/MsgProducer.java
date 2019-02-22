package com.redhat.amq_samples;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.activemq.command.ActiveMQDestination;

import com.redhat.util.JNDIResolver;

public class MsgProducer
      {
         
         private Session session;
         private MessageProducer _producer;
         private TextMessage msg;

         public void init(String url, String destination)throws NamingException, JMSException
           {
               
             session = JNDIResolver.init(url);
             _producer = session.createProducer(ActiveMQDestination.createDestination(destination, (byte) 1));
             msg = session.createTextMessage();
             //session.start();
           }

         public void send(String message) throws JMSException 
           {
                      msg.setText(message);
                      _producer.send(msg);
           }

         public void close() throws JMSException 
           {
               _producer.close();
               JNDIResolver.close();
           }

         public static void main(String[] args) throws Exception 
           {
           /*    if (args.length != 1) 
                  {
                     System.out.println("Usage: java QueueSend URL");
                     return;
                  }*/
              
               
               MsgProducer producer = new MsgProducer();
               
               producer.init("tcp://localhost:61616","queue://test_queue");
               readAndSend(producer);
               producer.close();
           }

         private static void readAndSend(MsgProducer qs) throws IOException, JMSException
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
