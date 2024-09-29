package com.redis.kafkaspring.inbound

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class TransactionConsumerConfig(
) {
    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<String, String>(
        consumerProps,
        StringDeserializer(),
        StringDeserializer(),
        )

    private val consumerProps = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10001, localhost:10002, localhost:10003",
        ConsumerConfig.GROUP_ID_CONFIG to "group-1",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
    )

    @Bean
    fun transactionKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, String>) =
        ConcurrentKafkaListenerContainerFactory<String, String>()
            .also {
                it.consumerFactory = consumerFactory
            }
}
