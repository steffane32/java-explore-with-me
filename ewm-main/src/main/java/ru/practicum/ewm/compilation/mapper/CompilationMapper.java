package ru.practicum.ewm.compilation.mapper;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation) {
        if (compilation == null) {
            return null;
        }
        Set<EventShortDto> eventDtos = new HashSet<>();
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
            eventDtos = EventMapper.toShortDtoSet(compilation.getEvents(), Collections.emptyMap());
        }
        return new CompilationDto(
                compilation.getId(),
                compilation.getPinned(),
                compilation.getTitle(),
                eventDtos
        );
    }
}