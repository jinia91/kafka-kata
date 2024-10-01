package com.redis.kafkaspring.inbound

import TestProto
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.redis.kafkaspring.QueueMessage
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig
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
    fun consumerFactory() = DefaultKafkaConsumerFactory<String, TestProto>(
        consumerProps,
        StringDeserializer(),
        KafkaProtobufDeserializer()
        )

    private val consumerProps = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10001, localhost:10002, localhost:10003",
        ConsumerConfig.GROUP_ID_CONFIG to "group-1",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        "schema.registry.url" to "http://localhost:8081",
        "specific.protobuf.value.type" to TestProto::class.java,

        )

    @Bean
    fun transactionKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, TestProto>) =
        ConcurrentKafkaListenerContainerFactory<String, TestProto>()
            .also {
                it.consumerFactory = consumerFactory
            }
}
