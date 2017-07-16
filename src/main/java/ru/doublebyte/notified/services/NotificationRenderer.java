package ru.doublebyte.notified.services;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Render notification by template name
 */
public class NotificationRenderer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRenderer.class);

    private static final String TEMPLATE_DIRECTORY = "templates";

    private PebbleEngine pebbleEngine;
    private Map<String, PebbleTemplate> templates = new HashMap<>();

    ///////////////////////////////////////////////////////////////////////////

    public NotificationRenderer() {
        pebbleEngine = new PebbleEngine.Builder().build();
    }

    /**
     * Render given template to string
     * @param templateName Template file name without extension
     * @param parameters Parameters
     * @return Rendered string
     */
    public String render(String templateName, Map<String, String> parameters) {
        if (!templates.containsKey(templateName)) {
            templates.put(templateName, load(templateName));
        }

        PebbleTemplate template = templates.get(templateName);
        if (template == null) {
            throw new RuntimeException("Template file not found or cannot be loaded");
        }

        try {
            Map<String, Object> templateParameters = new HashMap<>();
            parameters.forEach(templateParameters::put);

            Writer writer = new StringWriter();
            template.evaluate(writer, templateParameters);
            return writer.toString();
        } catch (Exception e) {
            logger.warn("Unable to render template {}: {}", templateName, e.getMessage());
            throw new RuntimeException("Unable to render template");
        }
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * Load template from file
     * @param templateName Template file name without extension
     * @return Loaded template or null when not found or error occurred
     */
    private PebbleTemplate load(String templateName) {
        logger.info("Loading template from file '{}'", templateName);

        Path templatePath = Paths.get(TEMPLATE_DIRECTORY, String.format("%s.pebble", templateName));

        if (!Files.exists(templatePath)) {
            logger.warn("Template file '{}' not exists", templateName);
            return null;
        }

        try {
            return pebbleEngine.getTemplate(templatePath.toString());
        } catch (Exception e) {
            logger.warn("Unable to load template file '{}': {}", templateName, e.getMessage());
            return null;
        }
    }

}
