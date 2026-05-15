package com.svenson95.track_e_backend.database.service;

import com.svenson95.track_e_backend.database.dto.WorkoutDTO;
import com.svenson95.track_e_backend.database.mapper.WorkoutMapper;
import com.svenson95.track_e_backend.database.model.Workout;
import com.svenson95.track_e_backend.database.repository.WorkoutRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

  private final WorkoutRepository workoutRepository;
  private final WorkoutMapper workoutMapper;
  private final UserService userService;

  public WorkoutService(
      WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserService userService) {
    this.workoutRepository = workoutRepository;
    this.workoutMapper = workoutMapper;
    this.userService = userService;
  }

  public List<WorkoutDTO> findUserWorkouts(String userId) {
    return workoutRepository.findByUserId(userId).orElse(List.of()).stream()
        .map(workoutMapper::toDto)
        .toList();
  }

  public WorkoutDTO createWorkout(WorkoutDTO dto) {
    Workout workout = workoutMapper.toEntity(dto);
    Workout saved = workoutRepository.save(workout);
    userService.addWorkoutIdToUserWorkouts(saved.getUserId(), saved.getWorkoutId());
    return workoutMapper.toDto(saved);
  }

  public WorkoutDTO changeWorkoutName(WorkoutDTO dto) {
    Workout workout =
        workoutRepository
            .findByWorkoutId(dto.getWorkoutId())
            .orElseThrow(() -> new RuntimeException("Workout not found"));
    workout.setName(dto.getName());
    Workout updatedWorkout = workoutRepository.save(workout);
    return workoutMapper.toDto(updatedWorkout);
  }

  public WorkoutDTO changeWorkoutList(WorkoutDTO dto) {
    Workout workout =
        workoutRepository
            .findByWorkoutId(dto.getWorkoutId())
            .orElseThrow(() -> new RuntimeException("Workout not found"));
    Workout newList = workoutMapper.toEntity(dto);
    workout.setList(newList.getList());
    Workout updatedWorkout = workoutRepository.save(workout);
    return workoutMapper.toDto(updatedWorkout);
  }

  public boolean deleteById(String id) {
    return workoutRepository
        .findById(id)
        .map(
            workout -> {
              workoutRepository.deleteById(id);
              userService.removeWorkoutFromList(workout.getUserId(), workout.getWorkoutId());
              return true;
            })
        .orElse(false);
  }
}
