package com.egym.exercise.service.impl;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.egym.exercise.common.Constants;
import com.egym.exercise.dto.ExerciseDTO;
import com.egym.exercise.exception.ExerciseServiceException;
import com.egym.exercise.repository.ExerciseRepository;
import com.egym.exercise.service.ExerciseValidationService;
import com.egym.exercise.vo.Error;

@Service
public class ExerciseValidationServiceImpl implements ExerciseValidationService {
	
	private ExerciseRepository exerciseRepository;
	
	private Properties errorProperties;
	
	public ExerciseValidationServiceImpl(ExerciseRepository exerciseRepository, Properties errorProperties) {
		this.exerciseRepository = exerciseRepository;
		this.errorProperties = errorProperties;
	}

	@Override
	public void validateExerciseForUser(int userId, LocalDateTime startDateTime, int exerciseId) throws ExerciseServiceException {
		List<ExerciseDTO> exerciseDTOs = exerciseRepository.findByUserId(userId);
		for(ExerciseDTO exerciseDTO : exerciseDTOs) {
			long seconds = Duration.between(exerciseDTO.getStartTime(), startDateTime).getSeconds();
			if(seconds >= 0 && seconds < exerciseDTO.getDuration() && exerciseId != exerciseDTO.getExcerciseId()) {
				throwExerciseServiceException(Constants.EXERCISE_ALREADY_EXISTS, userId, startDateTime);
			}
		}
	}

	@Override
	public void validateUpdateExercise(int exerciseId, int userId, String exerciseType) throws ExerciseServiceException {
		Optional<ExerciseDTO> optionalExerciseDTO = exerciseRepository.findById(exerciseId);	
		if(optionalExerciseDTO.isPresent()) {
			ExerciseDTO exerciseDTO = optionalExerciseDTO.get();
			if(userId != exerciseDTO.getUserId()) {
				throwExerciseServiceException(Constants.INVALID_USER_ID_DURING_UPDATE);
			} else if(!exerciseType.equalsIgnoreCase(exerciseDTO.getExerciseType())) {
				throwExerciseServiceException(Constants.INVALID_EXERCISE_TYPE_DURING_UPDATE);
			}
		} else {
			throwExerciseServiceException(Constants.INVALID_EXERCISE_ID, exerciseId);
		}
	}
	
	private void throwExerciseServiceException(String code, Object ... arguments) throws ExerciseServiceException {
		String message = errorProperties.getProperty(code);
		if(arguments != null && arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
		Error errorVO = new Error();
		errorVO.setErrorCode(code);
		errorVO.setErrorMessage(message);
		throw new ExerciseServiceException(message, errorVO);
	}
}
