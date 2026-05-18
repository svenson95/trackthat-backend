package com.svenson95.track_e_backend.database.controller;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.service.LogWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs-workout")
public class LogWorkoutController {

  @Autowired private LogWorkoutService logWorkoutService;

  @GetMapping("/get/{date}")
  public LogWorkoutDTO getLogWorkouts(@PathVariable String date) {
    return logWorkoutService.findLogWorkout(date);
  }

  @GetMapping("/get/latest-log/{exercise}")
  public LogWorkoutDTO getLatestLogForExercise(@PathVariable String exercise) {
    return logWorkoutService.findLatestLogForExercise(exercise);
  }

  @PostMapping("/add/set/{logId}/{userId}")
  public LogWorkoutDTO addSetToLog(
      @PathVariable String logId,
      @PathVariable String userId,
      @RequestBody LogWorkoutDTO.SetItemDTO setDto) {
    return logWorkoutService.updateOrCreateLog(logId, setDto, userId);
  }

  @PutMapping("/update/{logId}/{setIndex}")
  public LogWorkoutDTO updateSetInLog(
      @PathVariable String logId,
      @PathVariable String setIndex,
      @RequestBody LogWorkoutDTO.SetItemDTO setDto) {
    return logWorkoutService.updateSetInLog(logId, setIndex, setDto);
  }

  @DeleteMapping("/delete/{logId}/${setIndex}")
  public ResponseEntity<?> deleteSetInLog(
      @PathVariable String logId, @PathVariable String setIndex) {
    return logWorkoutService.deleteSetInLog(logId, setIndex);
  }
}
