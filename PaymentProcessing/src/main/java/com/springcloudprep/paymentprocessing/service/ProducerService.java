package com.springcloudprep.paymentprocessing.service;

import com.springcloudprep.amqp.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Import(RabbitMqConfig.class)
public class ProducerService {
    private final RabbitTemplate rabbitTemplate;
    private String exchangeName;
    private String queueName;
    private String routingKey;

    public ProducerService(RabbitTemplate rabbitTemplate,
                           @Value("${rabbitmq.exchange-name}") String exchangeName,
                           @Value("${rabbitmq.queue-name}") String queueName,
                           @Value("${rabbitmq.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    public void publishNotification(Object object) {
        log.info("Publishing message...... {}", object);
        rabbitTemplate.convertAndSend(exchangeName,routingKey, object);
        log.info("Published message...... {}", object);
    }
}
