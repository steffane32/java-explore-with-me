package ru.practicum.ewm.location.mapper;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.model.Location;

public class LocationMapper {

    public static LocationDto toDto(Location location) {
        if (location == null) return null;
        return new LocationDto(location.getId(), location.getLat(), location.getLon());
    }

    public static Location toEntity(LocationDto locationDto) {
        if (locationDto == null) return null;
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        return location;
    }
}