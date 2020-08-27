package com.egym.exercise.types;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public enum ExerciseType {

	RUNNING("RUNNING", 2),
	SWIMMING("SWIMMING", 3),
	STRENGTH_TRAINING("STRENGTH_TRAINING",3),
	CIRCUIT_TRAINING("CIRCUIT_TRAINING",4);

	private String name;
	private int credit;
	
	ExerciseType(String type, int credit) {
		this.credit = credit;
		this.name = type;
	}

	public String getName() {
		return name;
	}

	public int getCredit() {
		return credit;
	}
}
