package com.lfae.api.models.services;

import com.lfae.api.exceptions.BusinessValidationException;
import com.lfae.api.models.dto.EventDto;
import com.lfae.api.models.entities.Event;
import com.lfae.api.models.repositories.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents(boolean onlyActives) {
        if (onlyActives) {
            return eventRepository.findByActiveTrue();
        }
        return eventRepository.findAll();
    }

    public Event findEvent(UUID id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found: " + id.toString())
        );
    }

    @Transactional
    public void removeEvent() {

    }

    @Transactional
    public EventDto saveEvent(EventDto dto) {
        if (dto.startDate() != null && dto.endDate() != null && dto.endDate().isBefore(dto.startDate())) {
            throw new BusinessValidationException("End date cannot be before start date", "EVENT_INVALID_DATES");
        }
        Event saved = eventRepository.save(toEntity(dto));
        return toDto(saved);
    }


    public EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getCreationDate(),
                event.getName(),
                event.getDescription(),
                event.getCountry(),
                event.getCity(),
                event.getAddress(),
                event.getStartDate(),
                event.getEndDate(),
                event.isActive());
    }

    public Event toEntity(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.id());
        event.setCreationDate(eventDto.creationDate());
        event.setName(eventDto.name());
        event.setDescription(eventDto.description());
        event.setCountry(eventDto.country());
        event.setCity(eventDto.city());
        event.setAddress(eventDto.address());
        event.setStartDate(eventDto.startDate());
        event.setEndDate(eventDto.endDate());
        event.setActive(eventDto.active());
        return event;
    }
}