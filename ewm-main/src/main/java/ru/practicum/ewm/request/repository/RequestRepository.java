package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.request.model.ParticipationRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequesterId(Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    Optional<ParticipationRequest> findByIdAndRequesterId(Long requestId, Long userId);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    Long countByEventIdAndStatus(Long eventId, ParticipationRequest.RequestStatus status);

    @Query("SELECT r.event.id, COUNT(r) FROM ParticipationRequest r WHERE r.event.id IN :eventIds AND r.status = 'CONFIRMED' GROUP BY r.event.id")
    Map<Long, Long> countConfirmedRequestsByEventIds(@Param("eventIds") List<Long> eventIds);
}