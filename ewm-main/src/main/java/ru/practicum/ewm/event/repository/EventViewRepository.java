package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.model.EventView;
import java.time.LocalDateTime;

public interface EventViewRepository extends JpaRepository<EventView, Long> {
    boolean existsByEventIdAndIpAndViewedAtAfter(Long eventId, String ip, LocalDateTime time);
}