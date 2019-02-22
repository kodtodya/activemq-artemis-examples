package com.redhat.amq_Qbrowser;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Queue;

public interface QueueBrowser {
    Queue getQueue() throws JMSException;
    String getMessageSelector() throws JMSException;
    Enumeration getEnumeration() throws JMSException;
    void close() throws JMSException;
}