package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(Long trainingId) {
        return Optional.empty();
    }

    @Override
    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> findAllByUserId(Long id) {
        return trainingRepository.findAllByUserId(id);
    }

    @Override
    public List<Training> getDoneUserTrainingsAfterDate(Long id, Date endTime) {
        return trainingRepository.findAllByUserIdAndEndTimeBefore(id, endTime);
    }

    @Override
    public List<Training> getAllByUserIdAndActivityType(Long id, ActivityType activityType) {
        return trainingRepository.findAllByUserIdAndActivityType(id, activityType);
    }

    @Override
    public Training createNewTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Integer updateTraining(TrainingDto trainingDto) {
        return trainingRepository.setTrainingInfoById(trainingDto.getActivityType(), trainingDto.getDistance(), trainingDto.getAverageSpeed(), trainingDto.getId());
    }
}
