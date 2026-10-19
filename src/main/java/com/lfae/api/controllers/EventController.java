package com.lfae.api.controllers;

import com.lfae.api.models.dto.EventDto;
import com.lfae.api.models.entities.Event;
import com.lfae.api.models.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventDto> getEvents(
            @RequestParam(defaultValue = "false") boolean onlyActives
    ) {

        return eventService.findAllEvents(onlyActives).stream().map(eventService::toDto).toList();
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable UUID eventId) {
        Event dbEvent = eventService.findEvent(eventId);
        return eventService.toDto(dbEvent);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@Valid @RequestBody EventDto dto) {
        return eventService.saveEvent(dto);
    }
}
