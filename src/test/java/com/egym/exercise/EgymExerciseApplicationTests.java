package com.egym.exercise;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.egym.exercise.common.Constants;
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
	
	private List<ExerciseDTO> exerciseDTOs;
	
	private int userId = 1;
	
	@BeforeEach
	public void init() throws IOException {
		MockitoAnnotations.initMocks(this);
		if(errorProperties != null) {
			errorProperties = ExerciseUtils.loadProperties("error-messages.properties");
		}
		exerciseValidationServiceImpl = new ExerciseValidationServiceImpl(exerciseRepository, errorProperties);
		exerciseServiceImpl = new ExerciseServiceImpl(exerciseValidationServiceImpl, exerciseRepository, errorProperties);
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
		
		ExerciseDTO invalidExerciseDTO = new ExerciseDTO();
		invalidExerciseDTO.setCalories(calories);
		invalidExerciseDTO.setDescription(description);
		invalidExerciseDTO.setDuration(duration);
		invalidExerciseDTO.setExcerciseId(2);
		invalidExerciseDTO.setExerciseType(exerciseType.toString());
		invalidExerciseDTO.setUserId(userId);
		invalidExerciseDTO.setStartTime(ExerciseUtils.parseStringToDate("2020-01-27T13:20:45Z"));
		exerciseDTOs = new ArrayList<>();
		exerciseDTOs.add(invalidExerciseDTO);
	}
	
	@Test
	public void create_exercise_succeed() throws ExerciseServiceException {
		when(exerciseRepository.save(any(ExerciseDTO.class))).thenReturn(exerciseDTO);
		Exercise savedExercise = exerciseServiceImpl.saveExercise(exerciseVO);
		Assert.assertNotNull(savedExercise.getId());
	}
	
	@Test
	public void create_exercise_failure_already_exists() {
		when(exerciseRepository.findByUserId(userId)).thenReturn(exerciseDTOs);
		when(exerciseRepository.save(any(ExerciseDTO.class))).thenReturn(exerciseDTO);
		ExerciseServiceException exerciseServiceException = assertThrows(ExerciseServiceException.class, () -> {
			exerciseServiceImpl.saveExercise(exerciseVO);
		});
		Assert.assertEquals(Constants.EXERCISE_ALREADY_EXISTS, exerciseServiceException.getErrorVO().getErrorCode());
	}
	
	@Test
	public void create_exercise_failure_invalid_date() {
		exerciseVO.setStartTime("2020-01-2713:20:45Z");//Invalid date
		when(exerciseRepository.save(any(ExerciseDTO.class))).thenReturn(exerciseDTO);
		ExerciseServiceException exerciseServiceException = assertThrows(ExerciseServiceException.class, () -> {
			exerciseServiceImpl.saveExercise(exerciseVO);
		});
		Assert.assertEquals(Constants.INVALID_EXERCISE_START_TIME, exerciseServiceException.getErrorVO().getErrorCode());
	}
	
}
