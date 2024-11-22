package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);
    List<Training> findAll();
    List<Training> findAllByUserId(Long id);
    List<Training> getDoneUserTrainingsAfterDate(Long id, Date endTime);
    List<Training> getAllByUserIdAndActivityType(Long id, ActivityType activityType);
    Training createNewTraining(Training training);
    Integer updateTraining(TrainingDto trainingDto);
}