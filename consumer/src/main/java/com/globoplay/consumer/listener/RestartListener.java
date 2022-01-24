package com.globoplay.consumer.listener;

import com.globoplay.consumer.constant.NotificationTypeConstant;
import com.globoplay.consumer.dto.SubscriptionDto;
import com.globoplay.consumer.entity.Subscription;
import com.globoplay.consumer.service.SubscriptionService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestartListener {

    @Autowired
    private SubscriptionService subscriptionService;

    @RabbitListener(queues = NotificationTypeConstant.RESTARTED)
    private void handleMessage(String messageBody) {
        Gson gson = new Gson();
        SubscriptionDto subscriptionDto = gson.fromJson(messageBody, SubscriptionDto.class);
        log.info("RESTARTED HandleMessage!!!");
        log.info(messageBody);
        try {
            Subscription subscription =  subscriptionService.restart(subscriptionDto);
            log.info("RESTARTED Successfully! SubscriptionID " + subscription.getId());
        } catch (Exception exception) {
            log.info("Error!!!" + exception.getMessage());
        }
    }
}
