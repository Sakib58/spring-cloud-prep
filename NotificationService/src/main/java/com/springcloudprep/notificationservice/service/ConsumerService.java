package com.springcloudprep.notificationservice.service;

import com.springcloudprep.sharedpackages.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsumerService {
    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void listenQueue(NotificationDto dto) throws InterruptedException {
        notificationService.sendNotification(dto);
    }
}
