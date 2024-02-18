package com.springcloudprep.notificationservice.service;

import com.springcloudprep.notificationservice.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, Long> {
}
