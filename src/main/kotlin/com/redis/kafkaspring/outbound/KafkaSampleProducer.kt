package com.redis.kafkaspring.outbound

import com.redis.kafkaspring.QueueMessage
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeaders
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class KafkaSampleProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    fun publish(event: QueueMessage) {
        kafkaTemplate.send(
            ProducerRecord(
                "sample",
                null,
                event.clientId,
                event.message,
                event.recordHeaders,
            ),
        )
    }

    private val QueueMessage.recordHeaders: RecordHeaders
        get() = RecordHeaders().apply {
            this.add("SubjectId", this@recordHeaders.subjectId.toByteArray())
        }
}
