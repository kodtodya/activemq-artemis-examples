package com.redhat.amq_Qbrowser;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

public interface QBrowserSession extends Runnable {

    QueueBrowser createBrowser(Queue queue) throws JMSException;

    QueueBrowser createBrowser(Queue queue, String messageSelector)
        throws JMSException;

}