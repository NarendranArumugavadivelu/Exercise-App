package com.egym.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.egym.exercise.service.RankingService;
import com.egym.exercise.vo.Ranking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/ranking")
public class RankingController {
	
	@Autowired
	private RankingService rankingService;

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Get ranking for a set of users.", 
			   description = "Calculate the ranking for the given user ids. The calculation is based on the exercises the user has done in the last 28 days. The list is sorted in descending order by user points.",
			   parameters = @Parameter(name = "userIds", in = ParameterIn.QUERY, style = ParameterStyle.FORM, explode = Explode.TRUE, description = "List of user ids to rank", required = true,
					   				  schema = @Schema(type = "array", format = "int64"))
			  )
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ranking calculated", content = @Content(schema = @Schema(implementation = Ranking.class))),
	})
	public @ResponseBody List<Ranking> getRanking(@RequestParam(required = true) List<Integer> userIds) {
		return  rankingService.getUserRanking(userIds);
	}
}
