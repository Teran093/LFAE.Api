package com.lfae.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EventsController {

    @GetMapping("/events")
    public Map<String, Object> getEvents(){
        Map<String, Object> events =  new HashMap<String, Object>();
        events.put("nombre", "Conf España");
        return events;
    }
}
