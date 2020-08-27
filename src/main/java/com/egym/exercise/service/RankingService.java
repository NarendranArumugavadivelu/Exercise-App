package com.egym.exercise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.egym.exercise.vo.Ranking;

@Service
public interface RankingService {

	public List<Ranking> getUserRanking(List<Integer> userIds);
}
