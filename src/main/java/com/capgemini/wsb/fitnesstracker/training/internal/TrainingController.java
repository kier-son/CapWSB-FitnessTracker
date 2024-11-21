package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody TrainingDto trainingDto) {
        return trainingService.createTraining(trainingDto);
    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingService.findAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable Long id) {
        TrainingDto training = trainingService.findTrainingById(id);
        return training != null ? ResponseEntity.ok(training) : ResponseEntity.notFound().build();
    }

    // Additional endpoints can be added here
}