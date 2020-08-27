package com.egym.exercise.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.egym.exercise.exception.ExerciseServiceException;

@Service
public interface ExerciseValidationService {

	public void validateExerciseForUser(int userId, LocalDateTime localDateTime, int exerciseId) throws ExerciseServiceException;
	
	public void validateUpdateExercise(int exerciseId, int userId, String exerciseType) throws ExerciseServiceException;
}
