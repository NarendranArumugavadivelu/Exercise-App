package com.egym.exercise.service;

import org.springframework.stereotype.Service;

import com.egym.exercise.exception.ExerciseServiceException;
import com.egym.exercise.vo.Exercise;

@Service
public interface ExerciseService {

	public Exercise saveExercise(Exercise exerciseVO) throws ExerciseServiceException;
	
	public Exercise updateExercise(Exercise exerciseVO, int exerciseId) throws ExerciseServiceException;
}
