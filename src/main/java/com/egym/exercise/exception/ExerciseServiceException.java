package com.egym.exercise.exception;

import java.io.Serializable;

import com.egym.exercise.vo.Error;

public class ExerciseServiceException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 1055400178965046307L;
	
	private final Error errorVO;
	
	public ExerciseServiceException(String message, Error errorVO) {
		super(message);
		this.errorVO = errorVO;
	}

	public Error getErrorVO() {
		return errorVO;
	}
}
