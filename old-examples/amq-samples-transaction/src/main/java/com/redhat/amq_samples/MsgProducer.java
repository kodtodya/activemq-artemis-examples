package com.redhat.amq_samples;
import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.redhat.util.JNDIResolver;

public class MsgProducer
      {
         
         private Session session;
         private MessageProducer _producer;
         private TextMessage msg;

         public void init(String url, String destinationName)throws NamingException, JMSException
           {
               
             session = JNDIResolver.init(url);
             //Destination destination = ActiveMQDestination.createDestination(destinationName, (byte) 1);
             //Destination destination = session.createTopic(destinationName);
             Destination destination = session.createQueue(destinationName);
            		 
            		 
             _producer = session.createProducer(destination);
             _producer.setTimeToLive(5000);
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

         public static void main(String[] args) 
           {
           /*    if (args.length != 1) 
                  {
                     System.out.println("Usage: java QueueSend URL");
                     return;
                  }*/
              
              try{ 
               MsgProducer producer = new MsgProducer();
               //producer.init("failover:(tcp://localhost:61616,tcp://localhost:61617)","queue://test_queue");
               producer.init("tcp://localhost:61616","test_queue");
               readAndSend(producer);
               producer.close();
              }catch(Exception exception){
            	  System.err.println(exception.getMessage());
            	  exception.printStackTrace();
              }
           }

         private static void readAndSend(MsgProducer qs) throws IOException, JMSException, InterruptedException
           {
                //String line="Test Message Body with counter = ";
               /* BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
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
                br.close();*/
                
                
                
                for(int i =0;i<=900000;i++){
                	System.out.println("sending msg: "+i);
                	qs.send(Long.toString(i));
                	System.out.println("sent msg:"+i);
                	if(i%5==0){
                		qs.session.commit();
                		System.out.println("session committed");}
                	Thread.sleep(100);
                }
                qs.session.rollback();                
            }

         
        }
