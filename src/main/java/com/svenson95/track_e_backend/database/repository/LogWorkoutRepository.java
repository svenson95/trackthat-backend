package com.svenson95.track_e_backend.database.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.svenson95.track_e_backend.database.model.LogWorkout;

public interface LogWorkoutRepository extends MongoRepository<LogWorkout, String> {
  Optional<LogWorkout> findByDate(String date);

  Optional<LogWorkout> findTopBySetsExerciseOrderByDateDesc(String exercise);

  Optional<LogWorkout> findByLogId(Long logId);

  Optional<LogWorkout> findByUserIdAndDate(String userId, String date);

  @Query("SELECT MAX(l.logId) FROM Log l WHERE l.user.id = :userId")
  Long findMaxLogIdByUserId(@Param("userId") Long userId);
}
