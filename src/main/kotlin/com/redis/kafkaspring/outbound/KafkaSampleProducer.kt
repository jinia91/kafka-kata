package com.redis.kafkaspring.outbound

import TestProto
import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class KafkaSampleProducer(
    private val kafkaTemplate: KafkaTemplate<String, TestProto>,
) {
    fun publish(event: QueueMessage) {
        val testProto = TestProto.newBuilder()
            .setMemberId(1)
            .setEmail("test1")
            .setPassword("test2")
            .build()

        val producerRecord = ProducerRecord(
            event.topic,
            event.partitions,
            event.key,
            testProto
        )

        producerRecord.headers().add("Metadata", "Metadata".toByteArray())

        kafkaTemplate.send(producerRecord)
    }
}
