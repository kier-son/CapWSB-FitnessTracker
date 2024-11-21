package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;
import java.util.List;

public interface TrainingService {
    /**
     * Retrieves all trainings
     */
    List<TrainingDto> findAllTrainings();

    /**
     * Finds trainings for a specific user
     */
    List<TrainingDto> findTrainingsByUser(Long userId);

    /**
     * Finds completed trainings after a specific date
     */
    List<TrainingDto> findCompletedTrainingsAfter(Date date);

    /**
     * Finds trainings by specific activity type
     */
    List<TrainingDto> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Creates a new training
     */
    TrainingDto createTraining(TrainingDto trainingDto);

    /**
     * Updates an existing training
     */
    TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto);

    /**
     * Deletes a training by ID
     */
    void deleteTraining(Long trainingId);

    TrainingDto findTrainingById(Long id);

    Training createTraining(Training training);

    TrainingDto createTraining(TrainingCreateDto trainingCreateDto);

    public class TrainingCreateDto {
    }
}