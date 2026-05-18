package com.svenson95.track_e_backend.database.service;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.mapper.LogWorkoutMapper;
import com.svenson95.track_e_backend.database.model.LogWorkout;
import com.svenson95.track_e_backend.database.repository.LogWorkoutRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LogWorkoutService {

  private final LogWorkoutRepository logWorkoutRepository;
  private final LogWorkoutMapper logWorkoutMapper;

  public LogWorkoutService(
      LogWorkoutRepository logWorkoutRepository, LogWorkoutMapper logWorkoutMapper) {
    this.logWorkoutRepository = logWorkoutRepository;
    this.logWorkoutMapper = logWorkoutMapper;
  }

  public ResponseEntity<?> findLogWorkout(String date) {
    ZoneId zone = ZoneId.of("Europe/Berlin");

    LocalDate targetDate = parseUnixTimestamp(date).atZone(zone).toLocalDate();

    long startOfDay = targetDate.atStartOfDay(zone).toInstant().toEpochMilli();

    long startOfNextDay = targetDate.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli();

    Optional<LogWorkout> existing =
        logWorkoutRepository.findAll().stream()
            .filter(
                log -> {
                  long logMillis = parseUnixTimestamp(log.getDate()).toEpochMilli();

                  return logMillis >= startOfDay && logMillis < startOfNextDay;
                })
            .findFirst();

    if (existing.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(logWorkoutMapper.toDto(existing.get()));
  }

  private Instant parseUnixTimestamp(String value) {
    long timestamp = Long.parseLong(value);

    if (String.valueOf(Math.abs(timestamp)).length() <= 10) {
      return Instant.ofEpochSecond(timestamp);
    }

    return Instant.ofEpochMilli(timestamp);
  }

  public LogWorkoutDTO findLatestLogForExercise(String exercise) {

    LogWorkout log =
        logWorkoutRepository
            .findTopBySetsExerciseOrderByDateDesc(exercise)
            .orElseThrow(() -> new RuntimeException("No log found"));

    List<LogWorkoutDTO.SetItemDTO> filteredSets =
        log.getSets().stream()
            .filter(set -> exercise.equals(set.getExercise()))
            .map(logWorkoutMapper::toDto)
            .toList();

    LogWorkoutDTO dto = logWorkoutMapper.toDto(log);
    dto.setSets(filteredSets);

    return dto;
  }

  public LogWorkoutDTO updateOrCreateLog(
      String date, LogWorkoutDTO.SetItemDTO setDto, String userId) {
    LogWorkout log =
        logWorkoutRepository
            .findByUserIdAndDate(userId, date)
            .orElseGet(
                () -> new LogWorkout(userId, System.currentTimeMillis(), date, new ArrayList<>()));

    if (log.getSets() == null) {
      log.setSets(new ArrayList<>());
    }

    log.getSets().add(logWorkoutMapper.toEntity(setDto));
    LogWorkout saved = logWorkoutRepository.save(log);
    return logWorkoutMapper.toDto(saved);
  }

  public LogWorkoutDTO updateSetInLog(
      String logId, String setIndex, LogWorkoutDTO.SetItemDTO setDto) {
    LogWorkout log =
        logWorkoutRepository
            .findByLogId(Long.valueOf(logId))
            .orElseThrow(() -> new RuntimeException("Log not found: " + logId));
    int index = Integer.parseInt(setIndex);

    if (log.getSets() == null || index < 0 || index >= log.getSets().size()) {
      throw new RuntimeException("Set index out of bounds: " + setIndex);
    }

    log.getSets().set(index, logWorkoutMapper.toEntity(setDto));
    LogWorkout saved = logWorkoutRepository.save(log);
    return logWorkoutMapper.toDto(saved);
  }

  public ResponseEntity<?> deleteSetInLog(String logId, String setIndex) {
    LogWorkout log =
        logWorkoutRepository
            .findByLogId(Long.valueOf(logId))
            .orElseThrow(() -> new RuntimeException("Log not found"));
    int index = Integer.parseInt(setIndex);
    log.getSets().remove(index);

    if (log.getSets().isEmpty()) {
      logWorkoutRepository.delete(log);
      return ResponseEntity.ok("Log deleted because it became empty");
    }

    LogWorkout saved = logWorkoutRepository.save(log);
    return ResponseEntity.ok(logWorkoutMapper.toDto(saved));
  }
}
