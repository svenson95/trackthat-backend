package com.svenson95.track_e_backend.database.model;

import com.svenson95.track_e_backend.database.dto.LogWorkoutDTO.SetItemDTO;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs-workout")
public class LogWorkout {
  @Id private String _id;
  private String userId; // MongoDB doc id
  private Long logId;
  private String date; // UnixTimestring

  private List<SetItem> sets;

  public LogWorkout() {}

  public LogWorkout(String userId, Long logId, String date, List<SetItem> sets) {
    this.userId = userId;
    this.logId = logId;
    this.date = date;
    this.sets = sets;
  }

  public String getId() {
    return _id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getLogId() {
    return logId;
  }

  public void setLogId(Long logId) {
    this.logId = logId;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public List<SetItem> getSets() {
    return sets;
  }

  public void setSets(List<SetItem> sets) {
    this.sets = sets;
  }

  public static class SetItem {
    private String exercise;
    private Long load;
    private Long reps;
    private Long itemId;
    private String time;
    private String note;

    public SetItem() {}

    public SetItem(String exercise, Long load, Long reps, Long itemId, String time, String note) {
      this.exercise = exercise;
      this.load = load;
      this.reps = reps;
      this.itemId = itemId;
      this.time = time;
      this.note = note;
    }

    public String getExercise() {
      return exercise;
    }

    public void setExercise(String exercise) {
      this.exercise = exercise;
    }

    public Long getLoad() {
      return load;
    }

    public void setLoad(Long load) {
      this.load = load;
    }

    public Long getReps() {
      return reps;
    }

    public void setReps(Long reps) {
      this.reps = reps;
    }

    public Long getItemId() {
      return itemId;
    }

    public void setItemId(Long itemId) {
      this.itemId = itemId;
    }

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    }

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }

    public static SetItem fromDto(SetItemDTO dto) {

      SetItem item = new SetItem();

      item.setExercise(dto.getExercise());
      item.setLoad(dto.getLoad());
      item.setReps(dto.getReps());
      item.setItemId(dto.getItemId());
      item.setTime(dto.getTime());
      item.setNote(dto.getNote());

      return item;
    }
  }
}
