package ru.doublebyte.notified;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ru.doublebyte.notified.configuration.TemplatesConfiguration;
import ru.doublebyte.notified.configuration.TransportsConfiguration;
import ru.doublebyte.notified.services.MessageSender;
import ru.doublebyte.notified.services.NotificationRenderer;

@Configuration
public class MainConfiguration {

    @Value("${notified.thread-pool-size}")
    public int threadPoolSize;

    @Bean
    @ConfigurationProperties(prefix = "notified")
    public TransportsConfiguration transportsConfiguration() {
        return new TransportsConfiguration();
    }

    @Bean
    @ConfigurationProperties(prefix = "notified")
    public TemplatesConfiguration templatesConfiguration() {
        return new TemplatesConfiguration();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolSize);
        return taskScheduler;
    }

    @Bean
    public NotificationRenderer notificationRenderer() {
        return new NotificationRenderer();
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender(
                notificationRenderer(),
                transportsConfiguration().getTransportMailSenders(),
                templatesConfiguration().getTemplates()
        );
    }

}
