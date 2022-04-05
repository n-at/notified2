package ru.doublebyte.notified.structs;

/**
 * Notification template configuration
 */
public class NotificationTemplate {

    private String transport;
    private String key;
    private String file;

    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String replyTo;
    private String subject;
    private String body;
    private String charset = "UTF-8";
    private boolean html = false;
    private boolean allowAttachments = false;

    private boolean overrideFrom = false;
    private boolean overrideTo = false;
    private boolean overrideCc = false;
    private boolean overrideBcc = false;
    private boolean overrideReplyTo = false;
    private boolean overrideSubject = false;
    private boolean overrideBody = true;

    ///////////////////////////////////////////////////////////////////////////

    public NotificationTemplate() {

    }

    ///////////////////////////////////////////////////////////////////////////

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isAllowAttachments() {
        return allowAttachments;
    }

    public void setAllowAttachments(boolean allowAttachments) {
        this.allowAttachments = allowAttachments;
    }

    public boolean isOverrideFrom() {
        return overrideFrom;
    }

    public void setOverrideFrom(boolean overrideFrom) {
        this.overrideFrom = overrideFrom;
    }

    public boolean isOverrideTo() {
        return overrideTo;
    }

    public void setOverrideTo(boolean overrideTo) {
        this.overrideTo = overrideTo;
    }

    public boolean isOverrideCc() {
        return overrideCc;
    }

    public void setOverrideCc(boolean overrideCc) {
        this.overrideCc = overrideCc;
    }

    public boolean isOverrideBcc() {
        return overrideBcc;
    }

    public void setOverrideBcc(boolean overrideBcc) {
        this.overrideBcc = overrideBcc;
    }

    public boolean isOverrideReplyTo() {
        return overrideReplyTo;
    }

    public void setOverrideReplyTo(boolean overrideReplyTo) {
        this.overrideReplyTo = overrideReplyTo;
    }

    public boolean isOverrideSubject() {
        return overrideSubject;
    }

    public void setOverrideSubject(boolean overrideSubject) {
        this.overrideSubject = overrideSubject;
    }

    public boolean isOverrideBody() {
        return overrideBody;
    }

    public void setOverrideBody(boolean overrideBody) {
        this.overrideBody = overrideBody;
    }
}
