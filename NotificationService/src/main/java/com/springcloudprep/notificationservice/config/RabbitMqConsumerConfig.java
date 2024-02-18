package com.springcloudprep.notificationservice.config;

import com.springcloudprep.amqp.config.RabbitMqConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMqConfig.class)
public class RabbitMqConsumerConfig {
}
