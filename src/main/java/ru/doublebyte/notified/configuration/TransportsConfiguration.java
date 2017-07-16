package ru.doublebyte.notified.configuration;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TransportsConfiguration {

    private Map<String, MailProperties> transports;

    public TransportsConfiguration() {

    }

    ///////////////////////////////////////////////////////////////////////////

    public Map<String, JavaMailSender> getTransportMailSenders() {
        Map<String, JavaMailSender> senders = new HashMap<>();

        transports.forEach((name, properties) -> {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            applyProperties(properties, javaMailSender);
            senders.put(name, javaMailSender);
        });

        return senders;
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Apply MailProperties to JavaMailSender
     * From {@link org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration}
     *
     * @param properties MailProperties
     * @param sender JavaMailSender
     */
    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if(properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }

        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if(properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }

        if(!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(properties.getProperties()));
        }
    }

    /**
     * Map to Properties
     * Also from @link org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration}
     *
     * @param source Map
     * @return Properties
     */
    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    ///////////////////////////////////////////////////////////////////////////

    public Map<String, MailProperties> getTransports() {
        return transports;
    }

    public void setTransports(Map<String, MailProperties> transports) {
        this.transports = transports;
    }

}
