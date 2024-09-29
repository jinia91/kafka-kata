package com.redis.kafkaspring.inbound

import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.MessageListener
import org.springframework.stereotype.Component

@Component
class KafkaSampleConsumer {
    @KafkaListener(
        topics = ["sample"],
        containerFactory = "transactionKafkaListenerContainerFactory",
    )
    fun consume(record: ConsumerRecord<String, String>) {
        println("Consumed: ${record.value()}")
    }
}