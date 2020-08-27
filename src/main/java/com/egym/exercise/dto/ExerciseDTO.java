package com.egym.exercise.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "exercise")
public class ExerciseDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "excercise_id")
	private int exerciseId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "exercise_type")
	private String exerciseType;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "calories")
	private int calories;
	
	public int getExcerciseId() {
		return exerciseId;
	}

	public void setExcerciseId(int excerciseId) {
		this.exerciseId = excerciseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}
}
