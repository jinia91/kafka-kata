package com.redis.kafkaspring

import com.redis.kafkaspring.outbound.KafkaSampleProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaSampleController(
    private val kafkaSampleProducer: KafkaSampleProducer,
) {
    @PostMapping("/kafka")
    fun kafka(@RequestBody message: QueueMessage) {
        kafkaSampleProducer.publish(message)
    }

    @PostMapping("/kafka-stream-start")
    fun kafkaStreamStart() {
        while (true) kafkaSampleProducer.publish(
            QueueMessage(
                "streamingTopic1",
                0,
                listOf("key1", "key2", "key3", "key4", "key5", "key6", "key7").random(),
                "value",
                "0"
            )
        )
    }
}