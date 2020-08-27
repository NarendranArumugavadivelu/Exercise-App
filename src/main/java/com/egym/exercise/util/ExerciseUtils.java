package com.egym.exercise.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.egym.exercise.common.Constants;
import com.egym.exercise.vo.Ranking;

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
	
	public static void main(String[] args) {
		List<Ranking> rankings = new ArrayList<>();
		
		Ranking ranking = new Ranking();
		ranking.setUserId(1);
		ranking.setPoints(100);
		LocalDateTime latestExerciseStartTime = LocalDateTime.parse("2020-01-03T13:21:00Z", dateTimeFormatter);
		ranking.setLatestExerciseStartTime(latestExerciseStartTime);
		rankings.add(ranking);
		
		Ranking ranking2 = new Ranking();
		ranking2.setUserId(2);
		ranking2.setPoints(200);
		LocalDateTime localDateTime = LocalDateTime.parse("2020-01-28T13:21:00Z", dateTimeFormatter);
		ranking2.setLatestExerciseStartTime(localDateTime);
		rankings.add(ranking2);
		
		Ranking ranking3 = new Ranking();
		ranking3.setUserId(3);
		ranking3.setPoints(200);
		LocalDateTime localDateTime2 = LocalDateTime.parse("2020-01-30T13:21:00Z", dateTimeFormatter);
		ranking3.setLatestExerciseStartTime(localDateTime2);
		rankings.add(ranking3);
		
		rankings = rankings.stream().sorted(Comparator.comparing(Ranking :: getPoints).thenComparing(Ranking :: getLatestExerciseStartTime).reversed()).collect(Collectors.toList());
		for(Ranking rank : rankings) {
			System.out.println(rank.getPoints());
		}
	}
	
}