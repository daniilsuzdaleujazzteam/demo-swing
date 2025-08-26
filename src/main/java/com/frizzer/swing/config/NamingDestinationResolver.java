package com.frizzer.swing.config;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.stereotype.Component;

@Component
public class NamingDestinationResolver implements DestinationResolver {

    public static final String DEFAULT_QUEUE_SUFFIX = "Queue";
    public static final String DEFAULT_TOPIC_SUFFIX = "Topic";

    @Override
    public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
            throws JMSException {
        if(destinationName.endsWith(DEFAULT_QUEUE_SUFFIX)){
            return session.createQueue(destinationName);
        } else if(destinationName.endsWith(DEFAULT_TOPIC_SUFFIX)){
            return session.createTopic(destinationName);
        }
        throw new JMSException("Unknown destination name: " + destinationName);
    }
}
