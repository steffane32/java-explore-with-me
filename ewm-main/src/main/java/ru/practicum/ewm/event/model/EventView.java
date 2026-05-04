package ru.practicum.ewm.event.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;
}