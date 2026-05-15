package com.svenson95.track_e_backend.database.controller;

import com.svenson95.track_e_backend.database.dto.WorkoutDTO;
import com.svenson95.track_e_backend.database.service.WorkoutService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

  @Autowired private WorkoutService workoutService;

  @GetMapping("/get/{userId}")
  public List<WorkoutDTO> getWorkouts(@PathVariable String userId) {
    return workoutService.findUserWorkouts(userId);
  }

  @PostMapping("/add")
  public WorkoutDTO addWorkout(@RequestBody WorkoutDTO workout) {
    return workoutService.createWorkout(workout);
  }

  @PostMapping("/change-name")
  public WorkoutDTO changeWorkoutName(@RequestBody WorkoutDTO workout) {
    return workoutService.changeWorkoutName(workout);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteWorkout(@PathVariable String id) {
    boolean deleted = workoutService.deleteById(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }
}
