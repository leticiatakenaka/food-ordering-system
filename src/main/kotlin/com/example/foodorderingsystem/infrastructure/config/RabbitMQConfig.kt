package com.example.foodorderingsystem.infrastructure.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun exchange(): TopicExchange = TopicExchange("orders.exchange")

    @Bean
    fun createdQueue(): Queue =
        Queue("orders.created.queue", true)

    @Bean
    fun confirmedQueue(): Queue =
        Queue("orders.confirmed.queue", true)

    @Bean
    fun createdOrderBinding(createdQueue: Queue, ordersExchange: TopicExchange): Binding =
        BindingBuilder.bind(createdQueue).to(ordersExchange).with("orders.created")

    @Bean
    fun confirmedOrderBinding(confirmedQueue: Queue, ordersExchange: TopicExchange): Binding =
        BindingBuilder.bind(confirmedQueue).to(ordersExchange).with("orders.confirmed")

    @Bean
    fun messageConverter(): MessageConverter =
        Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
        messageConverter: MessageConverter
    ): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = messageConverter
        return template
    }

    @Bean
    fun rabbitListenerContainerFactory(
        connectionFactory: ConnectionFactory,
        messageConverter: MessageConverter
    ): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(messageConverter)
        return factory
    }
}