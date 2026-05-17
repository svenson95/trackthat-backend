package com.svenson95.track_e_backend.database.mapper;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO;
import com.svenson95.track_e_backend.database.model.LogWorkout;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogWorkoutMapper {
  LogWorkoutDTO toDto(LogWorkout workout);

  LogWorkout toEntity(LogWorkoutDTO dto);

  List<LogWorkoutDTO> toDtoList(List<LogWorkout> workouts);

  List<LogWorkout> toEntityList(List<LogWorkoutDTO> dtos);
}
