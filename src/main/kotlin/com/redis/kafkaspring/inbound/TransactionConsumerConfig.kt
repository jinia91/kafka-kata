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
import org.springframework.kafka.listener.ContainerProperties

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
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10001, localhost:10002, localhost:10000",
        ConsumerConfig.GROUP_ID_CONFIG to "group-1",
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to "false",
        ConsumerConfig.ISOLATION_LEVEL_CONFIG to "read_committed",
        "schema.registry.url" to "http://localhost:8081",
        "specific.protobuf.value.type" to TestProto::class.java,

        )

    @Bean
    fun transactionKafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, TestProto>) =
        ConcurrentKafkaListenerContainerFactory<String, TestProto>()
            .also {
                it.consumerFactory = consumerFactory
                it.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
            }

//
//    @Bean
//    fun consumerFactory2() = DefaultKafkaConsumerFactory<String, TestProto>(
//        consumerProps2,
//        StringDeserializer(),
//        KafkaProtobufDeserializer()
//    )
//
//    private val consumerProps2 = mapOf(
//        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:10001, localhost:10002, localhost:10003",
//        ConsumerConfig.GROUP_ID_CONFIG to "group-2",
//        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
//        "schema.registry.url" to "http://localhost:8081",
//        "specific.protobuf.value.type" to TestProto::class.java,
//
//        )
//
//    @Bean
//    fun transactionKafkaListenerContainerFactory2(consumerFactory2: ConsumerFactory<String, TestProto>) =
//        ConcurrentKafkaListenerContainerFactory<String, TestProto>()
//            .also {
//                it.consumerFactory = consumerFactory2
//            }
}
