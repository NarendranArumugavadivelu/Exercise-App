package com.egym.exercise.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.egym.exercise.exception.ExerciseServiceException;
import com.egym.exercise.service.ExerciseService;
import com.egym.exercise.vo.Exercise;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/exercise")
public class ExerciseController {
	
	@Autowired
	private ExerciseService exerciseService;

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Insert a new exercise for a user.", description = "Persist a new exercise and generate its id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Exercise created", content = @Content(schema = @Schema(implementation = Exercise.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = Error.class)))
	})
	public @ResponseBody Exercise insertExercise(@Valid @RequestBody(required = true) Exercise exerciseVO) throws ExerciseServiceException {
		return exerciseService.saveExercise(exerciseVO);
	}
	
	
	@PutMapping(path = "/{exerciseId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Update an existing exercise for a user.", 
			   description = "Update an already persisted exercise. Exercise id, user id and exercise type are excluded from the update.",
			   parameters = @Parameter(name = "exerciseId", in = ParameterIn.PATH, description = "Id of the exercise to update", required = true, 
			   schema = @Schema(type = "integer", format = "int64")))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Exercise updated", content = @Content(schema = @Schema(implementation = Exercise.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input",  content = @Content(schema = @Schema(implementation = Error.class)))
	})
	public @ResponseBody Exercise updateExercise(@Valid @RequestBody(required = true) Exercise exerciseVO, @PathVariable(required = true) int exerciseId) throws ExerciseServiceException {
		return exerciseService.updateExercise(exerciseVO, exerciseId);
	}
}
