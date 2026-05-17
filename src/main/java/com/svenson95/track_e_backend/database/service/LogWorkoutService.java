package com.svenson95.track_e_backend.database.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.mapper.LogWorkoutMapper;
import com.svenson95.track_e_backend.database.model.LogWorkout;
import com.svenson95.track_e_backend.database.repository.LogWorkoutRepository;

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
}
