package com.svenson95.track_e_backend.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.service.LogWorkoutService;

@RestController
@RequestMapping("/api/logs-workout")
public class LogWorkoutController {

  @Autowired private LogWorkoutService logWorkoutService;

  @GetMapping("/get/{date}")
  public LogWorkoutDTO getWorkouts(@PathVariable String date) {
    return logWorkoutService.findLogWorkout(date);
  }

  @PutMapping("/update")
  public LogWorkoutDTO updateLogWorkout(@RequestBody LogWorkoutDTO log) {
    return logWorkoutService.updateLogWorkout(log);
  }
}
