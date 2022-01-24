package com.globoplay.api.config;

import com.globoplay.api.constant.NotificationTypeConstant;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConfiguration {
    private static final String EXCHANGE = "amq.direct";

    private AmqpAdmin amqpAdmin;

    public RabbitMQConfiguration(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String nomeFila){
        return new Queue(nomeFila, false, false, false);
    }

    private DirectExchange exchangeDirect() {
        return new DirectExchange(EXCHANGE);
    }

    private Binding binding(Queue queue, DirectExchange exchangeDirect){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchangeDirect.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void configuration(){
        Queue queuePurchase = this.queue(NotificationTypeConstant.PURCHASED);
        Queue queueRestart = this.queue(NotificationTypeConstant.RESTARTED);
        Queue queueCancel = this.queue(NotificationTypeConstant.CANCELED);
        DirectExchange exchangeDirect = this.exchangeDirect();
        Binding bindingPurchase = this.binding(queuePurchase, exchangeDirect);
        Binding bindingRestart   = this.binding(queueRestart, exchangeDirect);
        Binding bindingCancel   = this.binding(queueCancel, exchangeDirect);

        this.amqpAdmin.declareQueue(queuePurchase);
        this.amqpAdmin.declareQueue(queueRestart);
        this.amqpAdmin.declareQueue(queueCancel);
        this.amqpAdmin.declareExchange(exchangeDirect);
        this.amqpAdmin.declareBinding(bindingPurchase);
        this.amqpAdmin.declareBinding(bindingRestart);
        this.amqpAdmin.declareBinding(bindingCancel);
    }
}
