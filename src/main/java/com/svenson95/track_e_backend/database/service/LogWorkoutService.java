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

  public LogWorkoutDTO findLogWorkout(String date) {
    Instant instant = Instant.ofEpochMilli(Long.parseLong(date));
    LocalDate targetDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

    Optional<LogWorkout> existing =
        logWorkoutRepository.findAll().stream()
            .filter(
                log -> {
                  Instant logInstant = Instant.ofEpochMilli(Long.parseLong(log.getDate()));
                  LocalDate logDate = logInstant.atZone(ZoneId.systemDefault()).toLocalDate();
                  return logDate.equals(targetDate);
                })
            .findFirst();

    if (existing.isPresent()) {
      return logWorkoutMapper.toDto(existing.get());
    }

    LogWorkout newLog = new LogWorkout();
    newLog.setDate(date);
    LogWorkout saved = logWorkoutRepository.save(newLog);

    return logWorkoutMapper.toDto(saved);
  }

  public LogWorkoutDTO updateLogWorkout(LogWorkoutDTO dto) {
    LogWorkout updated = logWorkoutMapper.toEntity(dto);
    LogWorkout saved = logWorkoutRepository.save(updated);

    return logWorkoutMapper.toDto(saved);
  }

  public LogWorkoutDTO findLatestLogForExercise(String exerciseName) {
    return logWorkoutRepository
        .findLatestSetsByExerciseName(exerciseName)
        .map(
            log -> {
              List<LogWorkoutDTO.SetItemDTO> filteredSets =
                  log.getSets().stream()
                      .filter(set -> exerciseName.equals(set.getExercise()))
                      .map(logWorkoutMapper::toDto)
                      .toList();

              LogWorkoutDTO dto = logWorkoutMapper.toDto(log);
              dto.setSets(filteredSets);

              return dto;
            })
        .orElse(null);
  }

  public LogWorkoutDTO updateOrCreateLog(
      String logId, LogWorkoutDTO.SetItemDTO setDto, String userId) {
    LogWorkout log =
        logWorkoutRepository
            .findByLogId(Long.valueOf(logId))
            .orElseGet(
                () ->
                    new LogWorkout(
                        userId,
                        Long.valueOf(logId),
                        String.valueOf(System.currentTimeMillis()),
                        new ArrayList<>()));

    if (log.getSets() == null) {
      log.setSets(new ArrayList<>());
    }

    log.getSets().add(logWorkoutMapper.toEntity(setDto));

    LogWorkout saved = logWorkoutRepository.save(log);

    return logWorkoutMapper.toDto(saved);
  }
}
