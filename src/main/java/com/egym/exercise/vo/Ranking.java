package com.egym.exercise.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonSerialize
public class Ranking {

	@JsonProperty(value = "userId")
	@Schema(description = "User id", type = "integer", format = "int64", example = "1")
	private int userId;
	
	@JsonProperty(value = "points")
	@Schema(description = "Points scored by the user in the last 28 days", type = "number", format = "float", example = "1000.51")
	private float points;
	
	@JsonIgnore
	private LocalDateTime latestExerciseStartTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public LocalDateTime getLatestExerciseStartTime() {
		return latestExerciseStartTime;
	}

	public void setLatestExerciseStartTime(LocalDateTime latestExerciseStartTime) {
		this.latestExerciseStartTime = latestExerciseStartTime;
	}

}
