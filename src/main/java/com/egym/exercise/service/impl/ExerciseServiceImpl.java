package com.egym.exercise.service.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egym.exercise.common.Constants;
import com.egym.exercise.dto.ExerciseDTO;
import com.egym.exercise.exception.ExerciseServiceException;
import com.egym.exercise.repository.ExerciseRepository;
import com.egym.exercise.service.ExerciseService;
import com.egym.exercise.service.ExerciseValidationService;
import com.egym.exercise.types.ExerciseType;
import com.egym.exercise.util.ExerciseUtils;
import com.egym.exercise.vo.Error;
import com.egym.exercise.vo.Exercise;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired
	private ExerciseValidationService exerciseValidationService;
	
	@Autowired
	private Properties errorProperties;
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	@Override
	public Exercise saveExercise(Exercise exerciseVO) throws ExerciseServiceException {
		ExerciseDTO exerciseDTO = getExerciseDTO(exerciseVO, exerciseVO.getId());	
		exerciseDTO = exerciseRepository.save(exerciseDTO);
		exerciseVO = getExerciseVO(exerciseDTO);
		return exerciseVO;
	}
	
	@Override
	public Exercise updateExercise(Exercise exerciseVO, int exerciseId) throws ExerciseServiceException {
		exerciseValidationService.validateUpdateExercise(exerciseId, exerciseVO.getUserId(), exerciseVO.getType().getName());
		ExerciseDTO exerciseDTO = getExerciseDTO(exerciseVO, exerciseId);
		exerciseDTO.setExcerciseId(exerciseId);
		exerciseDTO = exerciseRepository.save(exerciseDTO);
		exerciseVO = getExerciseVO(exerciseDTO);
		return exerciseVO;
	}
	
	/**Method to get the transfer object from value object*/
	private ExerciseDTO getExerciseDTO(Exercise exerciseVO, int exerciseId) throws ExerciseServiceException {
		ExerciseDTO exerciseDTO = new ExerciseDTO();
		LocalDateTime startDateTime = getDateFromString(exerciseVO.getStartTime());
		exerciseValidationService.validateExerciseForUser(exerciseVO.getUserId(), startDateTime, exerciseId);
		exerciseDTO.setCalories(exerciseVO.getCalories());
		exerciseDTO.setDescription(exerciseVO.getDescription());
		exerciseDTO.setDuration(exerciseVO.getDuration());
		exerciseDTO.setExerciseType(exerciseVO.getType().getName());
		exerciseDTO.setStartTime(startDateTime);
		exerciseDTO.setUserId(exerciseVO.getUserId());
		return exerciseDTO;
	}
	
	/**Method to get the value object from transfer object*/
	private Exercise getExerciseVO(ExerciseDTO exerciseDTO) {
		Exercise exerciseVO = new Exercise();
		exerciseVO.setCalories(exerciseDTO.getCalories());
		exerciseVO.setDescription(exerciseDTO.getDescription());
		exerciseVO.setDuration(exerciseDTO.getDuration());
		exerciseVO.setId(exerciseDTO.getExcerciseId());
		exerciseVO.setStartTime(ExerciseUtils.formatDateToString(exerciseDTO.getStartTime()));
		exerciseVO.setType(ExerciseType.valueOf(exerciseDTO.getExerciseType()));
		exerciseVO.setUserId(exerciseDTO.getUserId());
		return exerciseVO;
	}
	
	/**Method to validate the date time in string format*/
	private LocalDateTime getDateFromString(String dateInString) throws ExerciseServiceException {
		LocalDateTime localDateTime = ExerciseUtils.parseStringToDate(dateInString);
		if(localDateTime == null) {
			String message = MessageFormat.format(errorProperties.getProperty(Constants.INVALID_EXERCISE_START_TIME), dateInString, Constants.DATE_TIME_FORMAT);
			Error errorVO = new Error();
			errorVO.setErrorCode(Constants.INVALID_EXERCISE_START_TIME);
			errorVO.setErrorMessage(message);
			throw new ExerciseServiceException(message, errorVO);
		}
		return localDateTime;
	}
}


