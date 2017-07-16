package ru.doublebyte.notified.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import ru.doublebyte.notified.structs.NotificationTemplate;
import ru.doublebyte.notified.structs.ServiceRequest;

import javax.mail.internet.MimeMessage;
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
     * Get and check notification template
     * @param request Service request
     */
    public NotificationTemplate getNotificationTemplate(ServiceRequest request) {
        String template = request.getTemplate();
        String key = request.getKey();

        if (!templates.containsKey(template)) {
            throw new IllegalArgumentException(String.format("Notification template with name '%s' not found", template));
        }

        NotificationTemplate notificationTemplate = templates.get(template);

        if (!notificationTemplate.getKey().equals(key)) {
            throw new IllegalArgumentException("Notification template key mismatch");
        }

        return notificationTemplate;
    }

    /**
     * Prepare and send notification by service request
     * Sending is asynchronous
     *
     * @param notificationTemplate Notification template
     * @param request Service request
     */
    @Async
    public void notify(NotificationTemplate notificationTemplate, ServiceRequest request) {
        logger.info("notify '{}'", request.getTemplate());

        JavaMailSender mailSender = transports.get(notificationTemplate.getTransport());

        MimeMessage message;
        try {
            message = createMessage(mailSender, notificationTemplate, request);
        } catch (Exception e) {
            logger.error(String.format("Message creation error for template '%s'", request.getTemplate()) , e);
            return;
        }

        try {
            mailSender.send(message);
        } catch (Exception e) {
            logger.error(String.format("Failed to send message for template '%s'", request.getTemplate()), e);
        }
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

    /**
     * Create notification message by request and notification template
     * @param mailSender Mail sender
     * @param notificationTemplate Notification template
     * @param request Service request
     * @return Message
     */
    private MimeMessage createMessage(JavaMailSender mailSender, NotificationTemplate notificationTemplate, ServiceRequest request) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, notificationTemplate.getCharset());

        try {
            //From
            if (notificationTemplate.isOverrideFrom() && request.getFrom() != null && !request.getFrom().isEmpty()) {
                helper.setFrom(request.getFrom());
            } else if (notificationTemplate.getFrom() != null && !notificationTemplate.getFrom().isEmpty()) {
                helper.setFrom(notificationTemplate.getFrom());
            } else {
                throw new Exception("From required");
            }

            //To
            if (notificationTemplate.isOverrideTo() && request.getTo() != null && !request.getTo().isEmpty()) {
                helper.setTo(request.getTo());
            } else if (notificationTemplate.getTo() != null && !notificationTemplate.getTo().isEmpty()) {
                helper.setTo(notificationTemplate.getTo());
            } else {
                throw new Exception("To required");
            }

            //Subject
            if (notificationTemplate.isOverrideSubject() && request.getSubject() != null && !request.getSubject().isEmpty()) {
                helper.setSubject(request.getSubject());
            } else if (notificationTemplate.getSubject() != null && !notificationTemplate.getSubject().isEmpty()) {
                helper.setSubject(notificationTemplate.getSubject());
            } else {
                throw new Exception("Subject required");
            }

            //Cc
            if (notificationTemplate.isOverrideCc() && request.getCc() != null) {
                helper.setCc(request.getCc());
            } else if (notificationTemplate.getCc() != null) {
                helper.setCc(notificationTemplate.getCc());
            }

            //Bcc
            if (notificationTemplate.isOverrideBcc() && request.getBcc() != null) {
                helper.setBcc(request.getBcc());
            } else if (notificationTemplate.getBcc() != null) {
                helper.setBcc(notificationTemplate.getBcc());
            }

            //ReplyTo
            if (notificationTemplate.isOverrideReplyTo() && request.getReplyTo() != null) {
                helper.setReplyTo(request.getReplyTo());
            } else if (notificationTemplate.getReplyTo() != null) {
                helper.setReplyTo(notificationTemplate.getReplyTo());
            }

            //Body
            String body = "";
            if (notificationTemplate.isOverrideBody()) {
                body = notificationRenderer.render(notificationTemplate.getFile(), request.getParameters());
            } else if (notificationTemplate.getBody() != null) {
                body = notificationTemplate.getBody();
            }
            helper.setText(body, notificationTemplate.isHtml());

            //Attachments
            if (notificationTemplate.isAllowAttachments()) {
                request.getAttachments().forEach((fileName, content) -> {
                    try {
                        helper.addAttachment(fileName, new ByteArrayResource(content));
                    } catch (Exception e) {
                        logger.warn("Failed to attach file '{}' in notification template '{}'", fileName, request.getTemplate());
                    }
                });
            }

            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
