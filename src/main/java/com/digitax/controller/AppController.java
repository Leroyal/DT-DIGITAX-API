package com.digitax.controller;



import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = {"About Application"}, description = "About Application")
public class AppController {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    
    /*
     * Version of the APP
     */
    public Map version() {
        return new HashMap() {{
            put("version", "0.1.0");
        }};
    }
}