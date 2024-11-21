package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingMapper {
    private final UserRepository userRepository;

    public TrainingDto toTrainingDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setUser(training.getUser());
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        return dto;
    }

    public List<TrainingDto> toTrainingDtos(List<Training> trainings) {
        return trainings.stream()
                .map(this::toTrainingDto)
                .collect(Collectors.toList());
    }



    public Training toEntity(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    public TrainingDto toDto(Training savedTraining) {
        TrainingDto dto = new TrainingDto();
        dto.setId(savedTraining.getId());
        dto.setUser(savedTraining.getUser());
        dto.setStartTime(savedTraining.getStartTime());
        dto.setEndTime(savedTraining.getEndTime());
        dto.setActivityType(savedTraining.getActivityType());
        dto.setDistance(savedTraining.getDistance());
        dto.setAverageSpeed(savedTraining.getAverageSpeed());
        return dto;


    }
}