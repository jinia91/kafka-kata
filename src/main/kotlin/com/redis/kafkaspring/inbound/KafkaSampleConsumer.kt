package com.redis.kafkaspring.inbound

import TestProto
import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Component

@Component
class KafkaSampleConsumer {
    @KafkaListener(
        topics = ["partition-over"],
        containerFactory = "transactionKafkaListenerContainerFactory",
    )
    fun consume(record: ConsumerRecord<String, TestProto>) {
        val testProto = record.value()
        println("Consumed: $testProto")
        println("Consumed: ${testProto.email}")
        println("Consumed: ${testProto.password}")
    }
}