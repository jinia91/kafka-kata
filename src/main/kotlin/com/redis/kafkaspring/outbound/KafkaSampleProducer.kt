package com.redis.kafkaspring.outbound

import TestProto
import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class KafkaSampleProducer(
    private val kafkaTemplate: KafkaTemplate<String, TestProto>,
) {
    fun publish(event: QueueMessage) {
        val testProto = TestProto.newBuilder()
            .setMemberId(1)
            .setEmail(event.email)
            .setPassword(event.password)
            .build()

        val producerRecord = ProducerRecord(
            event.topic,
            event.partitions,
            event.key,
            testProto
        )

        kafkaTemplate.executeInTransaction {
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
            kafkaTemplate.send(producerRecord)
        }
    }
}
