package com.egym.exercise;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.egym.exercise.dto.ExerciseDTO;
import com.egym.exercise.exception.ExerciseServiceException;
import com.egym.exercise.repository.ExerciseRepository;
import com.egym.exercise.service.impl.ExerciseServiceImpl;
import com.egym.exercise.service.impl.ExerciseValidationServiceImpl;
import com.egym.exercise.types.ExerciseType;
import com.egym.exercise.util.ExerciseUtils;
import com.egym.exercise.vo.Exercise;



@RunWith(MockitoJUnitRunner.class)
class EgymExerciseApplicationTests {

	@Mock
	private ExerciseRepository exerciseRepository;
	
	@Mock
	private Properties errorProperties;
	
	private ExerciseValidationServiceImpl exerciseValidationServiceImpl;
	
	private ExerciseServiceImpl exerciseServiceImpl;
	
	private Exercise exerciseVO;
	
	private ExerciseDTO exerciseDTO;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		exerciseValidationServiceImpl = new ExerciseValidationServiceImpl(exerciseRepository, errorProperties);
		exerciseServiceImpl = new ExerciseServiceImpl(exerciseValidationServiceImpl, exerciseRepository, errorProperties);
		
		int userId = 1;
		String description = "Swim 30 minutes a day";
		ExerciseType exerciseType = ExerciseType.RUNNING;
		int duration = 60;
		int calories = 199;
		String startTime = "2020-01-27T13:21:00Z";
		int exerciseId = 1;
		
		exerciseVO = new Exercise();
		exerciseVO.setUserId(userId);
		exerciseVO.setDescription(description);
		exerciseVO.setType(exerciseType);
		exerciseVO.setDuration(duration);
		exerciseVO.setCalories(calories);
		exerciseVO.setStartTime(startTime);
		
		exerciseDTO = new ExerciseDTO();
		exerciseDTO.setExcerciseId(exerciseId);
		exerciseDTO.setCalories(calories);
		exerciseDTO.setDescription(description);
		exerciseDTO.setDuration(duration);
		exerciseDTO.setExerciseType(exerciseType.toString());
		exerciseDTO.setUserId(userId);
		exerciseDTO.setStartTime(ExerciseUtils.parseStringToDate(startTime));
	}
	
	@Test
	public void create_exercise_succeed() throws ExerciseServiceException {
		when(exerciseRepository.save(any(ExerciseDTO.class))).thenReturn(exerciseDTO);
		Exercise savedExercise = exerciseServiceImpl.saveExercise(exerciseVO);
		Assert.assertNotNull(savedExercise.getId());
	}
	
}
