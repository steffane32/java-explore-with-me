package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByInitiatorId(Long userId, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findPublishedEvents(@Param("categories") List<Long> categories,
                                    @Param("paid") Boolean paid,
                                    @Param("rangeStart") LocalDateTime rangeStart,
                                    @Param("rangeEnd") LocalDateTime rangeEnd,
                                    Pageable pageable);

    @Query(value = "SELECT * FROM events e WHERE " +
            "(:users IS NULL OR e.initiator_id IN (:users)) " +
            "AND (:states IS NULL OR e.state IN (:states)) " +
            "AND (:categories IS NULL OR e.category_id IN (:categories)) " +
            "AND (CAST(:rangeStart AS TIMESTAMP) IS NULL OR e.event_date >= CAST(:rangeStart AS TIMESTAMP)) " +
            "AND (CAST(:rangeEnd AS TIMESTAMP) IS NULL OR e.event_date <= CAST(:rangeEnd AS TIMESTAMP))",
            nativeQuery = true)
    List<Event> findAllByAdminFilters(@Param("users") List<Long> users,
                                      @Param("states") List<String> states,
                                      @Param("categories") List<Long> categories,
                                      @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd,
                                      Pageable pageable);

    boolean existsByCategoryId(Long categoryId);
}