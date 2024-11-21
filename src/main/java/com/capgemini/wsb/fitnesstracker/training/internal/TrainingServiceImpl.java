package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingServiceImpl(
            TrainingRepository trainingRepository,
            UserRepository userRepository,
            TrainingMapper trainingMapper
    ) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findTrainingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return trainingRepository.findByUser(user).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findCompletedTrainingsAfter(Date date) {
        return trainingRepository.findByEndTimeAfter(date).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> findTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrainingDto createTraining(TrainingDto trainingDto) {
        // Validate user exists
        if (trainingDto.getUser() == null || trainingDto.getUser().getId() == null) {
            throw new IllegalArgumentException("User must be specified for training");
        }

        userRepository.findById(trainingDto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Training training = trainingMapper.toEntity(trainingDto);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toDto(savedTraining);
    }

    @Override
    @Transactional
    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto) {
        // Find existing training
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found with ID: " + trainingId));

        // Update fields
        existingTraining.setStartTime(trainingDto.getStartTime());
        existingTraining.setEndTime(trainingDto.getEndTime());
        existingTraining.setActivityType(trainingDto.getActivityType());
        existingTraining.setDistance(trainingDto.getDistance());
        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());

        // Save updated training
        Training updatedTraining = trainingRepository.save(existingTraining);
        return trainingMapper.toDto(updatedTraining);
    }

    @Override
    @Transactional
    public void deleteTraining(Long trainingId) {
        // Verify training exists before deleting
        if (!trainingRepository.existsById(trainingId)) {
            throw new IllegalArgumentException("Training not found with ID: " + trainingId);
        }
        trainingRepository.deleteById(trainingId);
    }

    @Override
    public TrainingDto findTrainingById(Long id) {
        return null;
    }
    @Override
    public Training createTraining(Training training) {
        // Validate user
        if (training.getUser() == null || training.getUser().getId() == null) {
            throw new IllegalArgumentException("User must be specified for training");
        }

        // Ensure user exists
        User user = userRepository.findById(training.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the validated user
        training.setUser(user);

        return trainingRepository.save(training);
    }

    @Override
    public TrainingDto createTraining(TrainingCreateDto trainingCreateDto) {
        return null;
    }
}