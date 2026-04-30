package ru.practicum.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public void saveHit(EndpointHitDto hitDto) {
        EndpointHit hit = new EndpointHit();
        hit.setApp(hitDto.getApp());
        hit.setUri(hitDto.getUri());
        hit.setIp(hitDto.getIp());
        hit.setTimestamp(hitDto.getTimestamp());

        statsRepository.save(hit);
        log.info("Сохранён хит: app={}, uri={}, ip={}, timestamp={}",
                hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp());
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (Boolean.TRUE.equals(unique)) {
            return statsRepository.findUniqueHits(start, end, uris);
        } else {
            return statsRepository.findAllHits(start, end, uris);
        }
    }
}