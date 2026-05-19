package com.svenson95.track_e_backend.database.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.svenson95.track_e_backend.database.model.LogWorkout;

public interface LogWorkoutRepository extends MongoRepository<LogWorkout, String> {
  Optional<LogWorkout> findByDate(String date);

  Optional<LogWorkout> findTopBySetsExerciseOrderByDateDesc(String exercise);

  Optional<LogWorkout> findByLogId(Long logId);

  Optional<LogWorkout> findByUserIdAndDate(String userId, String date);

  Optional<LogWorkout> findTopByUserIdOrderByLogIdDesc(String userId);
}
