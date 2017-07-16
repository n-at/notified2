package ru.doublebyte.notified.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.doublebyte.notified.services.MessageSender;
import ru.doublebyte.notified.structs.ServiceRequest;
import ru.doublebyte.notified.structs.ServiceResponse;

import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final MessageSender messageSender;

    @Autowired
    public IndexController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    ///////////////////////////////////////////////////////////////////////////

    @PostMapping("/")
    public ServiceResponse index(@RequestParam Map<String, String> params) {
        ServiceRequest request;

        try {
            request = ServiceRequest.parse(params);
        } catch (Exception e) {
            logger.info("Request parsing error: {}", e.getMessage());
            return ServiceResponse.error(e.getMessage());
        }

        try {
            messageSender.notify(request);
        } catch (Exception e) {
            return ServiceResponse.error(e.getMessage());
        }

        return ServiceResponse.ok();
    }

}
