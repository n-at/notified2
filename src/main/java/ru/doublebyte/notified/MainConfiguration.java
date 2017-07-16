package ru.doublebyte.notified;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.doublebyte.notified.services.NotificationRenderer;

@Configuration
public class MainConfiguration {

    @Value("${notified.thread-pool-size}")
    public int threadPoolSize;

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

}
