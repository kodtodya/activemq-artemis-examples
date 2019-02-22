package com.redhat.activemq.thread_consumer;

public class MyBean {

	public String callMe(String msg) {
     	return msg + ":" + Thread.currentThread();
     }
}
