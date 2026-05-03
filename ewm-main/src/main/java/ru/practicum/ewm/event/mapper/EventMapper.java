package ru.practicum.ewm.event.mapper;

import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventShortDto toShortDto(Event event) {
        if (event == null) return null;
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toDto(event.getCategory()),
                0L,
                event.getEventDate(),
                UserMapper.toShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                0L
        );
    }

    public static EventFullDto toFullDto(Event event) {
        if (event == null) return null;
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toDto(event.getCategory()),
                0L,
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                UserMapper.toShortDto(event.getInitiator()),
                LocationMapper.toDto(event.getLocation()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                0L
        );
    }

    public static Set<EventShortDto> toShortDtoSet(Set<Event> events) {
        if (events == null) {
            return null;
        }
        return events.stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toSet());
    }
}