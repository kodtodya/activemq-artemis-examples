package com.redhat.activemq.thread_consumer;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

public class CamelActiveMQSyncExample {
	
	 private Main main;
	 
	    public static void main(String[] args) throws Exception {
	    	CamelActiveMQSyncExample example = new CamelActiveMQSyncExample();
	        example.boot();
	    }
	 
	    public void boot() throws Exception {
	
			// create a Main instance
	        main = new Main();
	        // bind MyBean into the registry
	        main.bind("myBean", new MyBean());
	        // add routes
	        RouteBuilder routeBuilder = new MyRouteBuilder();
	       // routeBuilder.setContext(camelContext);
	        main.addRouteBuilder(routeBuilder);
	        main.run();
	    }
	 
	    class MyRouteBuilder extends RouteBuilder {
	        @Override
	        public void configure() throws Exception {
	        		from("activemq:queue:test_queue?username=admin&password=admin&transacted=true&concurrentConsumers=10&destination.consumer.prefetchSize=50&acceptMessagesWhileStopping=true")
	        		.to("bean:myBean?method=callMe").log(LoggingLevel.INFO,"${body}");
				}
	        }
}