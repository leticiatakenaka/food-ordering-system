package com.example.foodorderingsystem.config

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun exchange(): TopicExchange = TopicExchange("orders.exchange")

    @Bean
    fun queue(): Queue = Queue("orders.queue")

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding =
        BindingBuilder.bind(queue).to(exchange).with("orders.created")
}
