package com.egym.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.egym.exercise.dto.ExerciseDTO;
import com.egym.exercise.repository.ExerciseRepository;
import com.egym.exercise.service.ExerciseService;


@RunWith(MockitoJUnitRunner.class)
class EgymExerciseApplicationTests {

	@InjectMocks
	ExerciseService exerciseService;
	
	@Mock
	ExerciseRepository exerciseRepository;
	
	@Test
	public void test_getExercise() {
		ExerciseDTO exerciseDTO = new ExerciseDTO();
		exerciseDTO.setUserId(1);
		exerciseDTO.setCalories(100);
		exerciseDTO.setDescription("Test");
		exerciseDTO.setDuration(50);
		exerciseDTO.setExcerciseId(1);
		exerciseDTO.setExerciseType("RUNNING");
		exerciseDTO.setStartTime(LocalDateTime.now());
		
		List<ExerciseDTO> exerciseDTOs = new ArrayList<ExerciseDTO>();
		exerciseDTOs.add(exerciseDTO);
		
		when(exerciseRepository.findByUserId(1)).thenReturn(exerciseDTOs);
		
		assertEquals(1, exerciseDTOs.size());
	}

}
