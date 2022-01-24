package com.globoplay.consumer.listener;

import com.globoplay.consumer.constant.NotificationTypeConstant;
import com.globoplay.consumer.dto.SubscriptionDto;
import com.globoplay.consumer.entity.Subscription;
import com.globoplay.consumer.repository.SubscriptionRepository;
import com.globoplay.consumer.service.SubscriptionService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseListener {

    @Autowired
    private SubscriptionService subscriptionService;

    @RabbitListener(queues = NotificationTypeConstant.PURCHASED)
    private void handleMessage(String messageBody) {
        Gson gson = new Gson();
        SubscriptionDto subscriptionDto = gson.fromJson(messageBody, SubscriptionDto.class);
        log.info("PURCHASED HandleMessage!!!");
        log.info(messageBody);
        try {
            Subscription subscription =  subscriptionService.purchase(subscriptionDto);
            log.info("PURCHASED Successfully! SubscriptionID " + subscription.getId());
        } catch (Exception exception) {
            log.info("Error!!!" + exception.getMessage());
        }
    }
}
