package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUserId(Long id);
    List<Training> findAllByUserIdAndEndTimeBefore(Long id, Date endTime);
    List<Training> findAllByUserIdAndActivityType(Long id, ActivityType activityType);
    @Transactional
    @Modifying
    @Query("update Training t set t.activityType = ?1, t.distance = ?2, t.averageSpeed = ?3 where t.id = ?4")
    Integer setTrainingInfoById(ActivityType activityType, Double distance, Double averageSpeed, Long id);
}
