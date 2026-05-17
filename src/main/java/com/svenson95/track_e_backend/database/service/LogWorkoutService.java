package com.svenson95.track_e_backend.database.service;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.mapper.LogWorkoutMapper;
import com.svenson95.track_e_backend.database.model.LogWorkout;
import com.svenson95.track_e_backend.database.repository.LogWorkoutRepository;
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

  public LogWorkoutDTO findLogWorkout(String logId) {
    return logWorkoutRepository.findByLogId(logId).map(logWorkoutMapper::toDto).orElseThrow();
  }

  public LogWorkoutDTO createLogWorkout(LogWorkoutDTO dto) {
    LogWorkout log = logWorkoutMapper.toEntity(dto);
    LogWorkout saved = logWorkoutRepository.save(log);
    return logWorkoutMapper.toDto(saved);
  }

  public boolean deleteById(String id) {
    return logWorkoutRepository
        .findById(id)
        .map(
            workout -> {
              logWorkoutRepository.deleteById(id);
              return true;
            })
        .orElse(false);
  }
}
