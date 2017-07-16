package ru.doublebyte.notified.structs;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ServiceRequestTest {

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        params.put("api_template", "template");
        params.put("api_key", "key");

        return params;
    }

    @Test
    public void constructorTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_template", null);
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_key", null);
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_template", "test");
        params.put("api_key", null);
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_template", "test");
        params.put("api_key", "");
        try {
            ServiceRequest.parse(params);
        } catch (Exception ignored) {
            fail();
        }

        params.put("api_template", "");
        params.put("api_key", "test");
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_template", null);
        params.put("api_key", "test");
        try {
            ServiceRequest.parse(params);
            fail();
        } catch (Exception ignored) {}

        params.put("api_template", "test");
        params.put("api_key", "test");
        try {
            ServiceRequest.parse(params);
        } catch (Exception ignored) {
            fail();
        }
    }

    @Test
    public void getTemplate() throws Exception {
        ServiceRequest request = ServiceRequest.parse(getParams());
        assertEquals("template", request.getTemplate());
    }

    @Test
    public void getKey() throws Exception {
        ServiceRequest request = ServiceRequest.parse(getParams());
        assertEquals("key", request.getKey());
    }

    @Test
    public void getFrom() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getFrom());

        params.put("api_from", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getFrom());

        params.put("api_from", " test ");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getFrom());
    }

    @Test
    public void getTo() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getTo());

        params.put("api_to", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getTo());

        params.put("api_to", " test ");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getTo());
    }

    @Test
    public void getCc() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getCc());

        params.put("api_cc", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getCc());

        params.put("api_cc", " test ");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getCc());
    }

    @Test
    public void getBcc() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getBcc());

        params.put("api_bcc", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getBcc());

        params.put("api_bcc", " test ");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getBcc());
    }

    @Test
    public void getReplyTo() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getReplyTo());

        params.put("api_reply_to", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getReplyTo());

        params.put("api_reply_to", " test ");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getReplyTo());
    }

    @Test
    public void getSubject() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getSubject());

        params.put("api_subject", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getSubject());

        params.put("api_subject", " test ");
        request = ServiceRequest.parse(params);
        assertEquals(" test ", request.getSubject());
    }

    @Test
    public void getBody() throws Exception {
        Map<String, String> params = getParams();

        ServiceRequest request = ServiceRequest.parse(params);
        assertNull(request.getBody());

        params.put("api_body", "test");
        request = ServiceRequest.parse(params);
        assertEquals("test", request.getBody());

        params.put("api_body", " test ");
        request = ServiceRequest.parse(params);
        assertEquals(" test ", request.getBody());
    }

    @Test
    public void getAttachments() throws Exception {
        byte[] content = "test".getBytes();
        byte[] contentEncoded = Base64.getEncoder().encode(content);
        Map<String, byte[]> attachments = new HashMap<>();
        attachments.put("file.txt", content);

        Map<String, String> params = getParams();
        ServiceRequest request = ServiceRequest.parse(params);
        assertEquals(new HashMap<>(), request.getAttachments());

        params.put("api_attach_file.txt", new String(contentEncoded, Charset.defaultCharset()));
        request = ServiceRequest.parse(params);
        assertArrayEquals(attachments.get("file.txt"), request.getAttachments().get("file.txt"));
    }

    @Test
    public void getParameters() throws Exception {
        Map<String, String> params = getParams();
        ServiceRequest request = ServiceRequest.parse(params);
        assertEquals(new HashMap<>(), request.getParameters());

        Map<String, String> parameters = new HashMap<>();
        parameters.put("key", "value");

        params.put("api_from", "from");
        params.put("key", "value");
        request = ServiceRequest.parse(params);
        assertEquals(parameters, request.getParameters());
    }

}
