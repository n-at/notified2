package ru.doublebyte.notified.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.doublebyte.notified.structs.ServiceRequest;
import ru.doublebyte.notified.structs.ServiceResponse;

import java.util.Map;

@RestController
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostMapping("/")
    public ServiceResponse index(@RequestParam Map<String, String> params) {
        ServiceRequest request;

        try {
            request = ServiceRequest.parse(params);
        } catch (Exception e) {
            logger.info("Request parsing error: {}", e.getMessage());
            return ServiceResponse.error(e.getMessage());
        }

        //TODO request processing

        return ServiceResponse.ok();
    }

}
