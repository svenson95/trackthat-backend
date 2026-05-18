package com.svenson95.track_e_backend.database.repository;

import com.svenson95.track_e_backend.database.model.LogWorkout;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogWorkoutRepository extends MongoRepository<LogWorkout, String> {
  Optional<LogWorkout> findByDate(String date);

  Optional<LogWorkout> findLatestSetsByExercise(String exercise);

  Optional<LogWorkout> findByLogId(Long logId);
}
