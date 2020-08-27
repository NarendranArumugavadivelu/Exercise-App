package com.egym.exercise.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.egym.exercise.dto.ExerciseDTO;

@Repository
public interface ExerciseRepository extends CrudRepository<ExerciseDTO, Integer> {

	public List<ExerciseDTO> findByUserId(int userId);
	
	public List<ExerciseDTO> findByStartTimeBetweenAndUserIdIn(LocalDateTime startDate, LocalDateTime endDate, List<Integer> userIds);
	
}
