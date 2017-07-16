package ru.doublebyte.notified.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import ru.doublebyte.notified.structs.NotificationTemplate;
import ru.doublebyte.notified.structs.ServiceRequest;

import java.util.Map;

/**
 * Send notification message
 */
public class MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    private NotificationRenderer notificationRenderer;
    private Map<String, JavaMailSender> transports;
    private Map<String, NotificationTemplate> templates;

    ///////////////////////////////////////////////////////////////////////////

    public MessageSender(
            NotificationRenderer notificationRenderer,
            Map<String, JavaMailSender> transports,
            Map<String, NotificationTemplate> templates
    ) {
        this.notificationRenderer = notificationRenderer;
        this.transports = transports;
        this.templates = templates;

        checkTemplatesAndTransports();
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Prepare and send notification by service request
     * @param request Service request
     */
    public void notify(ServiceRequest request) {
        //TODO
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Check links between templates and transports
     */
    private void checkTemplatesAndTransports() {
        if (templates.isEmpty()) {
            logger.warn("No notification templates found");
        }

        templates.forEach((name, template) -> {
            if (!transports.containsKey(template.getTransport())) {
                throw new IllegalArgumentException(String.format("Transport '%s' not found for notification template '%s'",
                        template.getTransport(), name));
            } else {
                logger.info("Notification template '{}' uses transport '{}'", name, template.getTransport());
            }
        });
    }

}
