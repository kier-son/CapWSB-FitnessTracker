package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.UpdateTrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    @PostMapping
    public TrainingDto addTraining(@RequestBody UpdateTrainingDto updateTrainingDto) {
        Training training = trainingMapper.convertToEntity(updateTrainingDto);
        try {
            return trainingMapper.convertToDto(trainingService.createNewTraining(training));
        } catch (Exception e) {
            throw new DataIntegrityViolationException("User not exist in database with id: " + training.getUser().getId() + "error" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTraining(@RequestBody TrainingDto trainingDto) {
        if (trainingService.updateTraining(trainingDto) == 1) {
            return new ResponseEntity<>("Training updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAll().stream()
                .map(trainingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/user/all", params = "idUser")
    public List<TrainingDto> getTrainingsByUser(@RequestParam("idUser") Long id) {
        return trainingService.findAllByUserId(id).stream()
                .map(trainingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/user/all", params = {"idUser", "activityType"})
    public List<TrainingDto> getUserTrainingsByActivity(@RequestParam("idUser") Long id,@RequestParam("activityType") ActivityType activityType) {
        return trainingService.getAllByUserIdAndActivityType(id, activityType).stream()
                .map(trainingMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/user/all", params = {"idUser", "endTime"})
    public List<TrainingDto> getDoneUserTrainingsBeforeDate(@RequestParam("idUser") Long id, @RequestParam("endTime") String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return trainingService.getDoneUserTrainingsAfterDate(id, sdf.parse(endTime)).stream()
                .map(trainingMapper::convertToDto)
                .collect(Collectors.toList());
    }
}