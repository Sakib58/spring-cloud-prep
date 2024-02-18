package com.springcloudprep.notificationservice.service;

import com.springcloudprep.notificationservice.model.Notifications;
import com.springcloudprep.sharedpackages.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void sendNotification(NotificationDto notificationDto) {
        log.info("Sending notification :" + notificationDto);
        executorService.submit(() -> {
                try {
                    Thread.sleep(10000);
                    notificationRepository.save(
                            Notifications
                                    .builder()
                                    .userId(notificationDto.getUserId())
                                    .fromCurrency(notificationDto.getFromCurrency())
                                    .toCurrency(notificationDto.getToCurrency())
                                    .amount(notificationDto.getAmount())
                                    .amountInOwnCurrency(notificationDto.getAmountInOwnCurrency())
                                    .build()
                    );
                    log.info("Sent notification :" + notificationDto);
                }catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
        });
    }
}
