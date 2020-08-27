package com.egym.exercise.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egym.exercise.dto.ExerciseDTO;
import com.egym.exercise.repository.ExerciseRepository;
import com.egym.exercise.service.RankingService;
import com.egym.exercise.types.ExerciseType;
import com.egym.exercise.vo.Ranking;

@Service
public class RankingServiceImpl implements RankingService {
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Override
	/**Method to rank the list of users based on the points gained in last 28 days
	 * Get the list of exercises for the provided users between the time range
	 * Sort the exercise list from newest to oldest
	 * Group the exercise list by user id and exercises for each user is grouped by exercise type
	 */
	public List<Ranking> getUserRanking(List<Integer> userIds) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime startRange = currentDateTime.minusDays(28);
		LocalDateTime endRange = currentDateTime.minusDays(1);
		List<ExerciseDTO> exerciseDTOs = exerciseRepository.findByStartTimeBetweenAndUserIdIn(startRange, endRange, userIds);
		exerciseDTOs = exerciseDTOs.stream().sorted(Comparator.comparing(ExerciseDTO :: getStartTime).reversed()).collect(Collectors.toList());
		Map<Integer, List<ExerciseDTO>> userIdExerciseDetailsMap = exerciseDTOs.stream().collect(Collectors.groupingBy(ExerciseDTO :: getUserId));
		List<Ranking> rankingVOs = new ArrayList<>();
		for(Entry<Integer, List<ExerciseDTO>> userIdExerciseEntry : userIdExerciseDetailsMap.entrySet()) {
			Ranking rankingVO = new Ranking();
			float points = 0.0f;
			List<ExerciseDTO> userExerciseList = userIdExerciseEntry.getValue();
			rankingVO.setLatestExerciseStartTime(userExerciseList.get(0).getStartTime());
			Map<String, List<ExerciseDTO>> exerciseTypeDetailsMap = userExerciseList.stream().collect(Collectors.groupingBy(ExerciseDTO :: getExerciseType));
			for(Entry<String, List<ExerciseDTO>> exerciseTypeEntry : exerciseTypeDetailsMap.entrySet()) {
				points += getPointsForExercises(exerciseTypeEntry.getValue());
			}
			rankingVO.setUserId(userIdExerciseEntry.getKey());
			rankingVO.setPoints(points);
			rankingVOs.add(rankingVO);
		}
		rankingVOs = rankingVOs.stream().sorted(Comparator.comparing(Ranking :: getPoints).thenComparing(Ranking :: getLatestExerciseStartTime).reversed()).collect(Collectors.toList());
		return rankingVOs;
	}
	
	/**Method to calculate the points for list of exercises of a user. Calculated as follows
	 * a. For each started minuted, user gets one point. Example: 80 seconds will give 2 points
	 * b. One point for calorie. Example: 80 calories will give 80 points
	 * c. Each exercise has multiplication factor. Example: points gained * multiplication factor
	 * d. For each time user performed same exercise in last 28 days, the points will be reduced by 10%
	 */
	private float getPointsForExercises(List<ExerciseDTO> exerciseDTOs) {
		float totalPoints = 0.0f;
		int percentage = 100;
		for(ExerciseDTO exerciseDTO : exerciseDTOs) {
			int multiplicationFactor = ExerciseType.valueOf(exerciseDTO.getExerciseType()).getCredit();
			float duration = exerciseDTO.getDuration();
			float points = (float) Math.ceil(((duration)/60.0));
			points += exerciseDTO.getCalories();
			points *= multiplicationFactor;
			points = (points/100) * percentage;
			percentage -= 10;
			totalPoints += points;
		}
		return totalPoints;
	}

}
