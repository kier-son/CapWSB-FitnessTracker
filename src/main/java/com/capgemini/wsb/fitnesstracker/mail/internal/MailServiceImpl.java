package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MailServiceImpl {

    private final TrainingProvider trainingService;
    private final UserService userService;
    private final TrainingMapper trainingMapper;
    private final MailProperties mailProperties;
    private final JavaMailSender mailSender;

    @Scheduled(cron = "0 0 0 1 * *")
    public void sendRaportEveryMonth() {
        List<User> users = userService.findAllUsers();
        SimpleMailMessage message = new SimpleMailMessage();

        for (User user: users) {
            List<TrainingDto> trainings = trainingService.findAllByUserId(user.getId()).stream()
                    .map(trainingMapper::convertToDto)
                    .collect(Collectors.toList());
            message.setFrom(mailProperties.getFrom());
            message.setTo(user.getEmail());
            message.setSubject("Monthly raport");
            message.setText(trainings.toString());
            mailSender.send(message);
        }
    }
}
