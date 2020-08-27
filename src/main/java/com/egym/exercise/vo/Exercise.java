package com.egym.exercise.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.egym.exercise.types.ExerciseType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)

public class Exercise {

	@JsonProperty("id")
	@Schema(description = "Exercise id, this is ignored during the persist operations (insert/update)", type = "integer", format = "int64")
	private int id;
	
	@JsonProperty("userId")
	@Min(value = 1, message = "{exercise.userId.min}")
	@Schema(description = "User who did the exercise", type = "integer", format = "int64")
	private int userId;
	
	@JsonProperty("description")
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "{exercise.description.invalid}")
	@NotNull(message = "{exercise.description.notNull}")
	@Schema(description = "Description of the exercise, a non empty string containing only alphanumeric characters and spaces in between", type = "string")
	private String description;
	
	@JsonProperty("type")
	@NotNull(message = "{exercise.type.notNull}")
	@Schema(implementation = ExerciseType.class)
	private ExerciseType type;
	
	@JsonProperty("startTime")
	@NotNull(message = "{exercise.startTime.notNull}")
	@Schema(description = "ISO8601(https://tools.ietf.org/html/rfc3339#section-5.6) UTC date and time the user started the exercise", type = "string", format = "date-time", example = "2019-10-30T12:34:23Z")
	private String startTime;
	
	@JsonProperty("duration")
	@Min(value = 1, message = "{exercise.duration.min}")
	@Schema(description = "Duration of the exercise in seconds", type = "integer", format = "int64")
	private int duration;
	
	@JsonProperty("calories")
	@Min(value = 1, message = "{exercise.calories.min}")
	@Schema(description = "Calories burnt in the exercise", type = "integer", format = "int64")
	private int calories;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public ExerciseType getType() {
		return type;
	}

	public void setType(ExerciseType type) {
		this.type = type;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
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
