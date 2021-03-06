package ru.doublebyte.notified.structs;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Request to service
 */
public class ServiceRequest {

    /**
     * Template name
     */
    private String template;

    /**
     * Template api key
     */
    private String key;

    /**
     * Override of template parameters
     */
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String replyTo;
    private String subject;
    private String body;

    /**
     * Attachment files. key=file name, value=file content
     */
    private Map<String, byte[]> attachments = new HashMap<>();

    /**
     * Parameters for template rendering
     */
    private Map<String, Object> parameters = new HashMap<>();

    ///////////////////////////////////////////////////////////////////////////

    private ServiceRequest() {

    }

    public static ServiceRequest parse(Map<String, String> params) {
        ServiceRequest request = new ServiceRequest();

        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("no request parameters provided");
        }

        Map<String, Map<String, String>> mapParameters = new HashMap<>();
        Pattern mapParameterKeyPattern = Pattern.compile("^(.*)\\[(.*)]$");

        params.forEach((key, value) -> {
            if (key == null) {
                return;
            }

            String trimmedValue = value == null ? null : value.trim();

            switch (key) {
                case "api_template":
                    request.template = trimmedValue;
                    return;

                case "api_key":
                    request.key = trimmedValue;
                    return;

                case "api_from":
                    request.from = trimmedValue;
                    return;

                case "api_to":
                    request.to = trimmedValue;
                    return;

                case "api_cc":
                    request.cc = trimmedValue;
                    return;

                case "api_bcc":
                    request.bcc = trimmedValue;
                    return;

                case "api_reply_to":
                    request.replyTo = trimmedValue;
                    return;

                case "api_subject":
                    request.subject = value;
                    return;

                case "api_body":
                    request.body = value;
                    return;
            }

            if (key.startsWith("api_attach_")) {
                String fileName = key.substring("api_attach_".length());

                if (value == null) {
                    throw new IllegalArgumentException("Attachment field is null: " + fileName);
                }

                byte[] content = Base64.getMimeDecoder().decode(value);

                request.attachments.put(fileName, content);
            } else {
                Matcher matcher = mapParameterKeyPattern.matcher(key);

                if (matcher.matches()) {
                    String parameterName = matcher.group(1);
                    String parameterKey = matcher.group(2);

                    if (!mapParameters.containsKey(parameterName)) {
                        mapParameters.put(parameterName, new HashMap<>());
                    }

                    mapParameters.get(parameterName).put(parameterKey, value);
                } else {
                    request.parameters.put(key, value);
                }
            }
        });

        request.parameters.putAll(mapParameters);

        if (request.template == null) {
            throw new IllegalArgumentException("api_template required");
        }

        if (request.template.isEmpty()) {
            throw new IllegalArgumentException("api_template is empty");
        }

        if (request.key == null) {
            throw new IllegalArgumentException("api_key required");
        }

        return request;
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getTemplate() {
        return template;
    }

    public String getKey() {
        return key;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

}
