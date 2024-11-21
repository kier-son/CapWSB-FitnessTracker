package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
interface TrainingRepository extends JpaRepository<Training, Long> {
    // Custom query methods
    List<Training> findByUser(User user);
    List<Training> findByEndTimeAfter(Date date);
    List<Training> findByActivityType(ActivityType activityType);
}