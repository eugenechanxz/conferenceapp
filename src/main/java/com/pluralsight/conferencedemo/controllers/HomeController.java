package com.pluralsight.conferencedemo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    @RequestMapping("/") //request mapping for root
    public Map getStatus() {
        Map map = new HashMap<String, String>();
        map.put("app-version", appVersion); //map and put app-version and appVersion into map and return map
        return map; //jackson lib will automatically convert into Json payload
    }
}
