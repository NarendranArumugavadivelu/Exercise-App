package com.egym.exercise.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.egym.exercise.common.Constants;

public class ExerciseUtils {
	
	private ExerciseUtils() {
		
	}

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);
	
	/**Method to parse the string to a valid local date time*/
	public static LocalDateTime parseStringToDate(String input) {
		LocalDateTime localDateTime;
		try {
			localDateTime = LocalDateTime.parse(input, dateTimeFormatter);
		} catch(Exception exception) {
			localDateTime = null;
		}
		return localDateTime;
	}
	
	/**Method to convert the valid local date time to string*/
	public static String formatDateToString(LocalDateTime localDateTime) {
		return localDateTime.format(dateTimeFormatter);
	}
}