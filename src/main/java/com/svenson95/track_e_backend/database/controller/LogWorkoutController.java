package com.svenson95.track_e_backend.database.controller;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.service.LogWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs-workout")
public class LogWorkoutController {

  @Autowired private LogWorkoutService logWorkoutService;

  @GetMapping("/get/{logId}")
  public LogWorkoutDTO getWorkouts(@PathVariable String logId) {
    return logWorkoutService.findLogWorkout(logId);
  }

  @PostMapping("/add")
  public LogWorkoutDTO addWorkout(@RequestBody LogWorkoutDTO log) {
    return logWorkoutService.createLogWorkout(log);
  }
}
