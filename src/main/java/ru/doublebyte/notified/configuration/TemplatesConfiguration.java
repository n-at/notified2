package ru.doublebyte.notified.configuration;

import ru.doublebyte.notified.structs.NotificationTemplate;

import java.util.Map;

public class TemplatesConfiguration {

    private Map<String, NotificationTemplate> templates;

    public TemplatesConfiguration() {

    }

    public Map<String, NotificationTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, NotificationTemplate> templates) {
        this.templates = templates;
    }

}
